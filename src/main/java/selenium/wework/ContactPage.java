package selenium.wework;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContactPage extends BasePage{

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public ContactPage addMember(String name, String account, String mobile){
        click(By.xpath("(//a[contains(@class,'js_add_member')])[2]"));
        sendKeys(By.id("username"),name);
        sendKeys(By.id("memberAdd_acctid"),account);
        sendKeys(By.id("memberAdd_phone"),mobile);
        click(By.cssSelector(".member_colRight_operationBar:nth-child(3) > .js_btn_save"));
        return this;
    }

    public ContactPage deleteMember(String name){
        click(By.xpath("//td[@title='"+name+"']/parent::node()//input[@class='ww_checkbox']"));
        click(By.xpath("//div[contains(@class,'js_operationBar_footer')]//a[contains(@class,'ww_btn js_delete')]"));
        click(By.xpath("//a[@d_ck='submit']"));
        return this;
    }

    public ContactPage search(String name){
        sendKeys(By.id("memberSearchInput"),name);
        return this;
    }

    public ContactPage clearSearch(){
        clear(By.id("memberSearchInput"));
        return this;
    }

    public String getSearchResult(){
        return text(By.xpath("//div[@class='member_display_cover_detail_name']"));
    }

    public ContactPage addDepartment(String departmentName){
        click(By.xpath("//i[@class='member_colLeft_top_addBtn']"));
        click(By.xpath("//a[@class='js_create_party']"));
        sendKeys(By.xpath("(//input[@class='qui_inputText ww_inputText'])[1]"),departmentName);
        click(By.xpath("//span[@class='js_parent_party_name']"));
        click(By.xpath("(//a[@class='jstree-anchor'])"));
        click(By.xpath("//a[@d_ck='submit']"));
        return this;
    }

    public String departmentName(){
        return text(By.xpath("(//li[@class='ww_searchResult_item_Curr'])[1]"));
    }

    public boolean departmentInvisible(){
        return invisible(By.xpath("//ul[@id='search_party_list']/li"));
    }

    public ContactPage deleteDepartment(){
        //点击刚添加的部门
        click(By.xpath("(//i[@class='jstree-icon jstree-themeicon icon icon_folder_blue jstree-themeicon-custom'])[2]"));
        //点击右边点号
        click(By.xpath("(//span[@class='icon jstree-contextmenu-hover'])[2]"));
        //点击删除
        click(By.xpath("//a[@rel='3']"));
        //点击确定
        click(By.xpath("//a[@d_ck='submit']"));
        return this;
    }

}
