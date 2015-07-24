package test;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import com.entity.User;

public class UserTest {
	
	//@Test 
	public void createTable(){  
        //可以验证生成表是否正确  
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        factory.close();  
    }
	
	@Test
	public void insert(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        EntityManager em = factory.createEntityManager();  
        em.getTransaction().begin();  
        User user = new User();
        user.setValue("value3");
        user.setName("hmk3"); //person为托管状态  
        user.setPassword("pwd3");
        em.persist(user);
        em.getTransaction().commit();  
        em.close();  
        factory.close();  
	}
	
	@Test 
	public void update(){  
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        EntityManager em = factory.createEntityManager();  
        em.getTransaction().begin();  
        User user = em.find(User.class, 1);
        user.setValue("value");
        user.setName("hmk"); //person为托管状态  
        user.setPassword("pwd");
        em.merge(user);
        em.getTransaction().commit();  
        em.close();  
        factory.close();  
    }  
	
	@Test
	public void remove(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mysqlJPA");  
        EntityManager em = factory.createEntityManager();  
        em.getTransaction().begin();  
        User user = em.find(User.class, 2);
        em.remove(user);
        em.getTransaction().commit();  
        em.close();  
        factory.close();  
	}

}
