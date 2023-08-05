package app;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.jboss.jandex.Main;

import core.util.HibernateUtil;
import web.member.pojo.Member;

public class TestAPP {
     public static void main(String[] args) {
		SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		//select USERNAME ,NICKNAME
		//from MEMBER
		//where USERNAME = ? and PASSWORD =?
		CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
		CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
		//from MEMBER
		Root<Member> root =criteriaQuery.from(Member.class);
		//where USERNAME = ? and PASSWORD =?
		criteriaQuery.where(criteriaBuilder.and(criteriaBuilder.equal(root.get("username"),"admin" ),
				criteriaBuilder.equal(root.get("password"),"P@ssw0rd")));
		
		//select USERNAME , NICKNAME
		criteriaQuery.multiselect(root.get("username"),root.get("nickname"));
		//order by ID
		criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
		
		Member member = session.createQuery(criteriaQuery).uniqueResult();
		System.out.println(member.getNickname());
		
		
		
		
		
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
    	 
    	 //刪除測試
//    	 System.out.println(app.deleteById(3));
    	 
    	 //更新測試
//    	 Member newNMember = new Member();
//    	 newNMember.setId(1);
//    	 newNMember.setPass(false);
//    	 newNMember.setRoleId(1);
//    	 System.out.println(app.updateById(newNMember));
    	 
    	 //查詢
//    	 System.out.println(app.selectById(2).getNickname());
    	 //查多
//    	 for (Member member : app.selectAll()) {
//    	       System.out.println(member.getNickname());
//    	      }
    	 
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
     
     public int updateById(Member newMember) {
    	 SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
    	 Session session = sessionFactory.openSession();
    	 try {
			Transaction transaction=session.beginTransaction();
			 Member oldMember = session.get(Member.class, newMember.getId());
			 final Boolean pass = newMember.getPass();
			 if (pass != null) {
				 oldMember.setPass(pass);
			 }
			 
			 final Integer roleId = newMember.getRoleId();
			 if (roleId != null) {
				 oldMember.setRoleId(roleId);
			 }
			 
			 transaction.commit();
			 return 1;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
    	 return -1;
     }
     
     public Member selectById(Integer id) {
    	 SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
    	 Session session = sessionFactory.openSession();
    	 try {
			Transaction transaction=session.beginTransaction();
			 Member oldMember = session.get(Member.class, id);
			 transaction.commit();
			 return oldMember;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
    	 return null;
     }
     
     public List<Member> selectAll() {
    	 SessionFactory sessionFactory =HibernateUtil.getSessionFactory();
    	 Session session = sessionFactory.openSession();
    	 try {
			Transaction transaction=session.beginTransaction();
			Query<Member> query = session.createQuery(
					"SELECT new web.member.pojo.Member(username,nickname) FROM Member", Member.class);
			   List<Member> list = query.getResultList();
			    transaction.commit();
			    return list;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}
    	 return null;
     }
}
