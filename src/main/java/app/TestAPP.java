package app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.jboss.jandex.Main;

import core.util.HibernateUtil;
import web.member.pojo.Member;

public class TestAPP {
     public static void main(String[] args) {
//		SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
//		Session session = sessionFactory.openSession();
//		Member member =session.get(Member.class, 1);
//		System.out.println(member.getNickname());
//		
//		
//		HibernateUtil.shutdown();
//    	新增
//    	 Member member = new Member();
//    	 member.setUsername("使用者名稱");
//    	 member.setPassword("密碼");
//    	 member.setNickname("暱稱");
//    	 
    	 TestAPP app =new TestAPP();
//    	 app.insert(member);
//    	 System.out.println(member.getId());
    	 
    	 System.out.println(app.deleteById(3));
	}
     
     public Integer insert(Member member) {
    	 SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
    	 Session session = sessionFactory.openSession();
    	 try {
			Transaction transaction=session.beginTransaction();
			 session.persist(member);
			 transaction.commit();
			 return member.getId();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
    	 return null;
     }
     
     public int deleteById(Integer id) {
    	 SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
    	 Session session = sessionFactory.openSession();
    	 try {
			Transaction transaction=session.beginTransaction();
			 Member member = session.get(Member.class, id);
			 session.remove(member);
			 transaction.commit();
			 return 1;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
    	 return -1;
     }
}
