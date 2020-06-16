package selenium.wework;

import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestContact {

    static MainPage main;
    static ContactPage contactPage;

    @BeforeAll
    static void beforeAll() throws Exception{
        main=new MainPage();
        contactPage = main.toContactPage();
    }

    @BeforeEach
    void clearSearch(){
        contactPage.clearSearch();
    }

    @Test
    void addMemberTest(){
        String name = contactPage.addMember("zhangfan","3","15967126670").search("zhangfan").getSearchResult();
        assertEquals(name,"zhangfan");
    }

    @Test
    void searchMemberTest(){
        String name = contactPage.search("zhangfan").getSearchResult();
        assertEquals(name,"zhangfan");
    }

    @Test
    void deleteMemberTest(){
        contactPage.deleteMember("zhangfan");
        String name = contactPage.search("zhangfan").getSearchResult();
        assertThat(name, not("zhangfan"));
    }

    @Test
    void addDepartment(){
        String department = contactPage.addDepartment("测试部").search("测试部").departmentName();
        assertThat(department,is("测试部"));
    }

    @Test
    void deleteDepartment(){
        contactPage.deleteDepartment();
        assert (contactPage.search("测试部").departmentInvisible());
    }

    @AfterAll
    static void afterAll(){
        main.quit();
    }

}
