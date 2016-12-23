package hibernate.test;

import hibernate.test.dto.Student;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class TestHibernateEhcache {

    public static void main(String[] args) {
        storeData();

        //Open the hibernate session
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {

        /*    //First Level Cache
            Student student = (Student) session.load(Student.class, 1);
            System.out.println( student.getFirstName());

            //Retriving student object for second time with same id from same session

            student=(Student)session.load(Student.class,1);
            System.out.println(student.getFirstName());

            //session.evict(student);

            //Retriving student object for second time with same id from another session

            Session anotherSession = HibernateUtil.getSessionFactory().openSession();
            anotherSession.beginTransaction();
            student = (Student) anotherSession.load(Student.class, new Integer(1));
            System.out.println(student.getFirstName());
            anotherSession.getTransaction().commit();*/



            //Second Level Cache

            //Entity is fecthed very first time
            Student student = (Student) session.load(Student.class, new Integer(1));
			System.out.println(student.getFirstName());
			
			//fetch the student entity again
			student = (Student) session.load(Student.class, new Integer(1));
			System.out.println(student.getFirstName());
			
			session.evict(student);
			
			student = (Student) session.load(Student.class, new Integer(1));
			System.out.println(student.getFirstName());
			
			Session anotherSession = HibernateUtil.getSessionFactory().openSession();
			anotherSession.beginTransaction();
			
			student = (Student) anotherSession.load(Student.class, new Integer(1));
			System.out.println(student.getFirstName());
			
			anotherSession.getTransaction().commit();


        } finally {
            System.out.println(HibernateUtil.getSessionFactory().getStatistics().getEntityFetchCount());
            System.out.println(HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());

            session.getTransaction().commit();
            HibernateUtil.shutdown();
        }
    }

    private static void storeData() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Student student = new Student("sunny", "jaswal", "sunny@gmail.com", 19);


        session.save(student);

        session.getTransaction().commit();
    }

}
