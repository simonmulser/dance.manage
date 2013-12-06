package at.danceandfun.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import at.danceandfun.entity.Style;

@Transactional
@ContextConfiguration("classpath:test/test-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class StyleDaoTest {

    @Autowired
    private DaoBase<Style> styleDao;

    public static Style getValidStyle() {
        Style style = new Style();
        style.setSid(999);
        style.setName("Style");
        return style;
    }

    @Test
    public void testSave() {
        styleDao.update(getValidStyle());
    }

}
