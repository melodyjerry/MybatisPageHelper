package cn.xm.exam.daoTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.print.Doc;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.xm.exam.bean.exam.Exam;
import cn.xm.exam.bean.exam.ExamExample;
import cn.xm.exam.mapper.exam.ExamMapper;
/**
 * 此种方式是测试没有集成spring的方式
 * 使用的mybatis的配置是SqlMapConfig.xml
 * @author liqiang
 *
 */
public class PageHelperTest {

	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setUp() throws IOException {
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}


	@Test
	public void test1() {
 		SqlSession sqlSession = sqlSessionFactory.openSession();
 		//创建ExamMapper对象
 		ExamMapper examMapper = sqlSession.getMapper(ExamMapper.class);
 		
 		ExamExample examExample = new ExamExample();
 		ExamExample.Criteria criteria = examExample.createCriteria();
 		//只对紧邻的下一条select语句进行分页查询，对之后的select不起作用
 		PageHelper.startPage(1,8);
 		//上面pagehelper的设置对此查询有效，查到数据总共8条
        List<Exam> exams = examMapper.selectByExample(examExample);
        PageInfo<Exam> pageInfo = new PageInfo<>(exams);
        System.out.println("第一次查询的exams的大小:"+exams.size());
        for(Exam e:pageInfo.getList()){
        	System.out.println(e);
        }
        System.out.println("分页工具类中数据量"+pageInfo.getList().size());
        System.out.println();
		System.out.println("---------------华丽的分割线------------");
		System.out.println();
        //第二次进行查询:上面pagehelper的设置对此查询无效（查询所有的数据86条）
        List<Exam> exams2 = examMapper.selectByExample(examExample);
        //总共86条
        System.out.println("第二次查询的exams2的大小"+exams2.size());
        
	}
	/**
	 * 测试自己写的根据名称模糊查询考试
	 */
	@Test
	public void test2() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//创建examMapper对象
		ExamMapper examMapper = sqlSession.getMapper(ExamMapper.class);

		//只对紧邻的下一条select语句进行分页查询，对之后的select不起作用
		PageHelper.startPage(1,6);
		//上面pagehelper的设置对此查询有效，查到数据，总共6条
		List<Exam> exams = examMapper.selectAllExamsByName("厂级");
		PageInfo<Exam> pageInfo = new PageInfo<>(exams);
		System.out.println("第一次查询的exams的大小(受pageHelper影响):"+exams.size());
		for(Exam e:pageInfo.getList()){
			System.out.println(e);
		}
		System.out.println("分页工具类中数据量"+pageInfo.getList().size());
		System.out.println();
		System.out.println("---------------华丽的分割线------------");
		System.out.println();
		 //第二次进行查询:上面pagehelper的设置对此查询无效（查询所有的数据34条）
		List<Exam> exams2 = examMapper.selectAllExamsByName("厂级");
		System.out.println("第二次查询的exams2的大小(不受pageHelper影响)"+exams2.size());
		
	}
}
