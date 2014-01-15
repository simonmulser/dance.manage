package at.danceandfun.sat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import at.danceandfun.entity.Course;
import at.danceandfun.entity.CourseParticipant;
import at.danceandfun.entity.Participant;
import at.danceandfun.entity.Performance;

/**
 * This class works as a validator for the SAT solver. The input parameters
 * contain of the solution from the solver and the list of participants. In the
 * process of validating the solution, specific boolean variables are set for
 * every course and every restriction. The class ValidatedCourse works as a link
 * to store the restriction booleans for every course.
 * 
 */

public class SatValidator {

	private Map<Integer, Performance> performancePlan;
	private List<Participant> participantList;
	private List<Course> courseList1;
	private List<Course> courseList2;
	private List<Course> courseList3;

	public SatValidator(Map<Integer, Performance> performancePlan,
			List<Participant> participantList) {
		this.performancePlan = performancePlan;
		this.participantList = participantList;

		this.courseList1 = performancePlan.get(1).getCourses();
		this.courseList2 = performancePlan.get(2).getCourses();
		this.courseList3 = performancePlan.get(3).getCourses();
	}

	/**
	 * @summary Main method of the Validator. In here, all the methods for the
	 *          restrictions are called.
	 * @return 3 lists of ValidatedCourses. Each for one performance. Booleans
	 *         for the restrictions are stored for every course.
	 */
	public Map<Integer, Performance> validatePerformancePlan() {
		// Restriction - siblingsInSamePerformance
		this.courseList1 = validateSiblingsInSamePerformance(this.courseList1,
				this.participantList);
		this.courseList2 = validateSiblingsInSamePerformance(this.courseList2,
				this.participantList);
		this.courseList3 = validateSiblingsInSamePerformance(this.courseList3,
				this.participantList);

		// Restriction - MultipleCourseInSamePerformance
		this.courseList1 = validateMultipleCoursesInSamePerformance(
				this.courseList1, this.participantList);
		this.courseList2 = validateMultipleCoursesInSamePerformance(
				this.courseList2, this.participantList);
		this.courseList3 = validateMultipleCoursesInSamePerformance(
				this.courseList3, this.participantList);

		// Restriction - Not two of a kind
		this.courseList1 = validateNotTwoOfAKindRestriktion(this.courseList1);
		this.courseList2 = validateNotTwoOfAKindRestriktion(this.courseList2);
		this.courseList3 = validateNotTwoOfAKindRestriktion(this.courseList3);

		// Restriction - 2 slots brake
		this.courseList1 = validate2SlotBrakeRestriktion(this.courseList1,
				participantList);
		this.courseList2 = validate2SlotBrakeRestriktion(this.courseList2,
				participantList);
		this.courseList3 = validate2SlotBrakeRestriktion(this.courseList3,
				participantList);

		// Restriction - Advanced at the end
		this.courseList1 = validateAdvancedAtTheEndRestriktion(this.courseList1);
		this.courseList2 = validateAdvancedAtTheEndRestriktion(this.courseList2);
		this.courseList3 = validateAdvancedAtTheEndRestriktion(this.courseList3);

		performancePlan.get(1).setCourses(courseList1);
		performancePlan.get(2).setCourses(courseList2);
		performancePlan.get(3).setCourses(courseList3);

		performancePlan = validatedBalancedAmountOfSpectators(performancePlan);

		performancePlan = validatedBalancedAgeGroup(performancePlan);

		return performancePlan;
	}

	/**
	 * @summary This method validates the restriction for nonconsecutive
	 *          ballets. Every course is inspected and the boolean for the
	 *          specific restriction is set to 'true' if the constraint is
	 *          violated
	 * @param validatedCourseList
	 *            Gets a list of ValidatedCourses as an input. This class
	 *            contains a course and booleans for the violation of
	 *            restrictions
	 * @return list of ValidatedCourses with the updated booleans of
	 *         balletRestriction
	 */
	private List<Course> validateNotTwoOfAKindRestriktion(
			List<Course> validatedCourseList) {

		int countGap = 999;
		int lastBallet = -1;

		for (int i = 0; i < validatedCourseList.size(); i++) {
			Course currentCourse = validatedCourseList.get(i);
			if (currentCourse.getStyle().getName().equals("Ballett")) {
				if (countGap == 0) {
					if (lastBallet != -1) {
						validatedCourseList.get(lastBallet)
								.setBalletRestriction(true);
					}
					validatedCourseList.get(i).setBalletRestriction(true);
					validatedCourseList.get(i).setViolationOfRestriktions(true);

				}
				lastBallet = i;
				countGap = 0;
			} else {
				countGap++;
			}
		}

		return validatedCourseList;
	}

	/**
	 * @summary This method validates the restriction for 2 courses brake for
	 *          participants that dance in both courses. Every course is
	 *          inspected and the boolean for the specific restriction is set to
	 *          'true' if the constraint is violated
	 * @param validatedCourseList
	 *            Gets a list of ValidatedCourses as an input. This class
	 *            contains a course and booleans for the violation of
	 *            restrictions
	 * @return list of ValidatedCourses with the updated booleans of
	 *         twoBreaksRestriction
	 */
	private List<Course> validate2SlotBrakeRestriktion(
			List<Course> validatedCourseList, List<Participant> participantList) {
		List<Integer> participantIDList = new ArrayList<Integer>();
		List<Integer> courseIDList = new ArrayList<Integer>();
		Participant currentParticipant;

		for (int i = 0; i < participantList.size(); i++) {
			if (participantList.get(i).getCourseParticipants().size() > 1) {
				participantIDList.add(i);
			}
		}

		for (int id : participantIDList) {
			courseIDList.clear();
			currentParticipant = participantList.get(id);

			for (CourseParticipant currentCP : currentParticipant
					.getCourseParticipants()) {
				for (int i = 0; i < validatedCourseList.size(); i++) {

					if (validatedCourseList.get(i)
							.equals(currentCP.getCourse())) {
						courseIDList.add(i);
					}
				}
			}

			Collections.sort(courseIDList);

			if (courseIDList.size() > 1) {
				for (int i = 0; i < courseIDList.size() - 1; i++) {
					if (!(courseIDList.get(i) + 2 < courseIDList.get(i + 1))) {
						validatedCourseList.get(courseIDList.get(i))
								.setTwoBreaksRestriction(true);
						validatedCourseList.get(courseIDList.get(i + 1))
								.setTwoBreaksRestriction(true);
						validatedCourseList.get(i).setViolationOfRestriktions(
								true);
						validatedCourseList.get(courseIDList.get(i + 1))
								.setViolationOfRestriktions(true);
					}
				}
			}
		}

		return validatedCourseList;
	}

	/**
	 * @summary This method validates the restriction for advanced courses have
	 *          to dance at the end of the performance. Every course is
	 *          inspected and the boolean for the specific restriction is set to
	 *          'true' if the constraint is violated
	 * @param validatedCourseList
	 *            Gets a list of ValidatedCourses as an input. This class
	 *            contains a course and booleans for the violation of
	 *            restrictions
	 * @return list of ValidatedCourses with the updated booleans of
	 *         advancedAtEndRestriction
	 */
	private List<Course> validateAdvancedAtTheEndRestriktion(
			List<Course> validatedCourseList) {
		List<Integer> idList = new ArrayList<Integer>();
		int sizeOfValidatedCourseList = validatedCourseList.size();

		for (int i = 0; i < sizeOfValidatedCourseList; i++) {
			if (validatedCourseList.get(i).getAmountPerformances() == 3) {
				idList.add(i);
			}
		}

		Collections.sort(idList);
		Collections.reverse(idList);

		for (int i = 0; i < idList.size(); i++) {
			if (!(idList.get(i) == sizeOfValidatedCourseList - (i + 1))) {
				validatedCourseList.get(idList.get(i))
						.setAdvancedAtEndRestriction(true);
				validatedCourseList.get(idList.get(i))
						.setViolationOfRestriktions(true);

			}
		}

		return validatedCourseList;
	}

	/**
	 * @summary This method validates the restriction for a balanced amount of
	 *          spectators in every performance. Every course is inspected and
	 *          the boolean for the specific restriction is set to 'true' if the
	 *          constraint is violated
	 * @param performanceMap
	 *            Gets a map of 3 performancess as an input. This class contains
	 *            the list of courses
	 * @return map containing 3 performances with the updated booleans of
	 *         advancedAtEndRestriction
	 */
	private Map<Integer, Performance> validatedBalancedAmountOfSpectators(
			Map<Integer, Performance> performanceMap) {
		List<Integer> amountSpectatorList = new ArrayList<Integer>();
		boolean isBalanced = true;

		for (int i = 1; i <= 3; i++) {
			List<Course> validatedCourseList = performanceMap.get(i)
					.getCourses();
			int spectatorAmount = 0;

			for (Course currentCourse : validatedCourseList) {
				if (!currentCourse.isDummyCourse()) {
					spectatorAmount += currentCourse.getEstimatedSpectators()
							.getValue() + 1;
				}
			}

			amountSpectatorList.add(spectatorAmount);
		}

		Collections.sort(amountSpectatorList);

		if (Math.abs(amountSpectatorList.get(0)
				- amountSpectatorList.get(amountSpectatorList.size() - 1)) > 6) {
			isBalanced = false;
		}

		if (!isBalanced) {
			for (int i = 1; i <= 3; i++) {
				List<Course> validatedCourseList = performanceMap.get(i)
						.getCourses();

				for (Course currentCourse : validatedCourseList) {
					currentCourse.setBalancedAmountOfSpectators(true);
				}
				performanceMap.get(i).setCourses(validatedCourseList);
			}
		}

		return performanceMap;
	}

	/**
	 * @summary This method validates the restriction for a balanced age group
	 *          in every performance. Every course is inspected and the boolean
	 *          for the specific restriction is set to 'true' if the constraint
	 *          is violated
	 * @param performanceMap
	 *            Gets a map of 3 performancess as an input. This class contains
	 *            the list of courses
	 * @return map containing 3 performances with the updated booleans of
	 *         advancedAtEndRestriction
	 */
	private Map<Integer, Performance> validatedBalancedAgeGroup(
			Map<Integer, Performance> performanceMap) {
		List<Integer> amountAgeGroupList = new ArrayList<Integer>();
		boolean isBalanced = true;

		for (int i = 1; i <= 3; i++) {
			List<Course> validatedCourseList = performanceMap.get(i)
					.getCourses();
			int ageGroupAmount = 0;

			for (Course currentCourse : validatedCourseList) {
				if (!currentCourse.isDummyCourse()) {
					ageGroupAmount += currentCourse.getAgeGroup().getValue() + 1;
				}
			}

			amountAgeGroupList.add(ageGroupAmount);
		}

		Collections.sort(amountAgeGroupList);

		if (Math.abs(amountAgeGroupList.get(0)
				- amountAgeGroupList.get(amountAgeGroupList.size() - 1)) > 6) {
			isBalanced = false;
		}

		if (!isBalanced) {
			for (int i = 1; i <= 3; i++) {
				List<Course> validatedCourseList = performanceMap.get(i)
						.getCourses();

				for (Course currentCourse : validatedCourseList) {
					currentCourse.setBalancedAgeGroup(true);
				}
				performanceMap.get(i).setCourses(validatedCourseList);
			}
		}

		return performanceMap;
	}

	private List<Course> validateMultipleCoursesInSamePerformance(
			List<Course> validatedCourseList, List<Participant> participantList) {
		List<Integer> notValidated = new ArrayList<Integer>();

		for (int i = 0; i < participantList.size(); i++) {
			boolean fitsRestriction = true;
			if (participantList.get(i).getCourseParticipants().size() > 1) {
				for (CourseParticipant currentCoPa : participantList.get(i)
						.getCourseParticipants()) {
					if (currentCoPa.getCourse().isEnabled()) {
						if (currentCoPa.getCourse().getAmountPerformances() == 1) {
							fitsRestriction = false;
						} else {
							break;
						}
					}
				}
				if (!fitsRestriction) {
					notValidated.add(i);
				}
			}
		}

		for (int k = 0; k < notValidated.size(); k++) {
			for (CourseParticipant currentCP : participantList.get(
					notValidated.get(k)).getCourseParticipants()) {
				for (int u = 0; u < validatedCourseList.size(); u++) {
					if (currentCP.getCourse().getCid() == validatedCourseList
							.get(u).getCid()) {
						validatedCourseList.get(u)
								.setMultipleGroupsSamePerformance(true);
					}
				}
			}
		}
		return validatedCourseList;
	}

	private List<Course> validateSiblingsInSamePerformance(
			List<Course> validatedCourseList, List<Participant> participantList) {
		List<Integer> sibCourses = new ArrayList<Integer>();
		List<Integer> siblingIDs = new ArrayList<Integer>();
		List<Integer> notValidated = new ArrayList<Integer>();

		for (int i = 0; i < participantList.size(); i++) {
			if (participantList.get(i).getSiblings().size() > 0) {
				sibCourses.add(i);
			}
		}

		for (int j = 0; j < sibCourses.size(); j++) {
			boolean alreadyIn = false;
			for (Participant sib : participantList.get(sibCourses.get(j))
					.getSiblings()) {
				for (int h = 0; h < siblingIDs.size(); h++) {
					if (participantList.get(siblingIDs.get(h)).getPid() == sib
							.getPid()) {
						alreadyIn = true;
					}
				}
			}
			if (!alreadyIn) {
				siblingIDs.add(sibCourses.get(j));
			}
		}

		for (int i = 0; i < siblingIDs.size(); i++) {
			boolean fitsRestriction = true;
			if (participantList.get(siblingIDs.get(i)).getCourseParticipants()
					.size() > 1) {
				for (CourseParticipant currentCoPa : participantList.get(
						siblingIDs.get(i)).getCourseParticipants()) {
					if (currentCoPa.getCourse().isEnabled()) {
						if (currentCoPa.getCourse().getAmountPerformances() == 1) {
						} else {
							break;
						}
					}
				}
				for (Participant participant : participantList.get(
						siblingIDs.get(i)).getSiblings()) {
					for (CourseParticipant currentCoPa : participant
							.getCourseParticipants()) {
						if (currentCoPa.getCourse().isEnabled()) {
							if (currentCoPa.getCourse().getAmountPerformances() == 1) {
								fitsRestriction = false;
							} else {
								break;
							}
						}
					}
				}

				if (!fitsRestriction) {
					notValidated.add(siblingIDs.get(i));
				}
			}
		}

		List<Integer> finalIDs = new ArrayList<Integer>();
		for (int h = 0; h < notValidated.size(); h++) {
			int counter = 0;
			boolean somethingWrong = false;
			for (CourseParticipant currentCP : participantList.get(
					notValidated.get(h)).getCourseParticipants()) {
				for (int i = 0; i < validatedCourseList.size(); i++) {
					if (currentCP.getCourse().getID() == validatedCourseList
							.get(i).getID()) {
						counter++;
					}
				}
			}
			if (counter != participantList.get(notValidated.get(h))
					.getCourseParticipants().size()) {
				somethingWrong = true;
			}

			for (Participant sibl : participantList.get(notValidated.get(h))
					.getSiblings()) {
				counter = 0;
				int courseAmount = 0;
				for (CourseParticipant currentCoP : sibl
						.getCourseParticipants()) {
					courseAmount++;
					for (int y = 0; y < validatedCourseList.size(); y++) {
						if (currentCoP.getCourse().getID() == validatedCourseList
								.get(y).getID()) {
							counter++;
						}
					}
				}
				if (counter != courseAmount) {
					somethingWrong = true;
				}
			}

			if (somethingWrong) {
				finalIDs.add(notValidated.get(h));
			}
		}

		for (int k = 0; k < finalIDs.size(); k++) {
			for (CourseParticipant currentCP : participantList.get(
					finalIDs.get(k)).getCourseParticipants()) {
				for (int u = 0; u < validatedCourseList.size(); u++) {
					if (currentCP.getCourse().getCid() == validatedCourseList
							.get(u).getCid()) {
						validatedCourseList.get(u).setSibsSamePerformance(true);
					}
				}
			}
		}

		for (int k = 0; k < finalIDs.size(); k++) {
			for (Participant participantt : participantList
					.get(finalIDs.get(k)).getSiblings()) {
				for (CourseParticipant currentCP : participantt
						.getCourseParticipants()) {
					for (int u = 0; u < validatedCourseList.size(); u++) {
						if (currentCP.getCourse().getCid() == validatedCourseList
								.get(u).getCid()) {
							validatedCourseList.get(u).setSibsSamePerformance(
									true);
						}
					}
				}
			}
		}

		return validatedCourseList;
	}
}