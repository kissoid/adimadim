/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean;

/**
 *
 * @author Adem
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;



@ConversationScoped
@Named(value="indexBean")
public class IndexBean implements Serializable{

    private List<String> articleList = new ArrayList<String>();
    
    public IndexBean() {

    }
    
    @PostConstruct
    private void init(){
        for(int i = 0; i < 3; i++){
            articleList.add("Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.");
        }        
    }

    public List<String> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<String> articleList) {
        this.articleList = articleList;
    }
    
    
}
