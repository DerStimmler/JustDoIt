/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package justdoit.todo.jpa;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import justdoit.common.jpa.User;

/**
 *
 * @author Lichter, Ansgar
 */
public class CategoryId implements Serializable{
    
    private String categoryName;
    
    private String username;

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public CategoryId() {
    }

    public CategoryId(String username, String categoryName) {
        this.username = username;
        this.categoryName = categoryName;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter and Setter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Equals and HashCode">
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof CategoryId)) {
            return false;
        }
        CategoryId categoryId = (CategoryId) o;
        if (!this.categoryName.equals(categoryId.getCategoryName())) {
            return false;
        }
        if (!this.username.equals(categoryId.getUsername())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
//        hash = 23 * hash + Objects.hashCode(this.username);
        hash = 23 * hash + Objects.hashCode(this.categoryName);
        return hash;
    }
    //</editor-fold>
}
