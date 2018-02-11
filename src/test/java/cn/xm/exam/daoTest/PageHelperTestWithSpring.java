package cn.xm.exam.daoTest;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.xm.exam.bean.exam.Exam;
import cn.xm.exam.bean.exam.ExamExample;
import cn.xm.exam.mapper.exam.ExamMapper;

/**
 * 与spring集成的mybatis的分页插件pagehelper的测试
 * @author liqiang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PageHelperTestWithSpring {
	
	@Resource
	private ExamMapper examMapper;

	@Test
	public void test1() {
 		//创建ExamMapper对象
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
	@Test
	public void test2() {
		//创建ExamMapper对象
		ExamExample examExample = new ExamExample();
		ExamExample.Criteria criteria = examExample.createCriteria();
		//只对紧邻的下一条select语句进行分页查询，对之后的select不起作用
		PageHelper.startPage(1,8);
		//上面pagehelper的设置对此查询有效，查到数据总共8条
		List<Exam> exams = examMapper.selectAllExamsByName("厂级");
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
		List<Exam> exams2 = examMapper.selectAllExamsByName("厂级");
		//总共86条
		System.out.println("第二次查询的exams2的大小"+exams2.size());
		
	}
}
