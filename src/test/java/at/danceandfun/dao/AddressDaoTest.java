package at.danceandfun.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Address;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AddressDaoTest {

    @Autowired
    private DaoBase<Address> addressDao;

    public static Address getValidAddress() {
        Address address = new Address();
        address.setCity("city");
        address.setDoor(45);
        address.setNumber("12");
        address.setStair(5);
        address.setStreet("street");
        address.setZip(1100);
        return address;
    }

    @Test
    public void testSave() {
        addressDao.persist(getValidAddress());
    }

}
