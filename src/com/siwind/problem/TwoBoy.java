package com.siwind.problem;

import java.util.Random;

/**
 * 问题描述：
 *     老张有两个孩子，已知至少有一个孩子是在星期二出生的男孩。问：两个孩子都是男孩的概率是多大？
 *     【假设生男生女的概率相等, 每周七天的每天出生概率相等】
 *     
 * 1) 编程思路:
 *     每个出生的孩子用有序对(性别, 出生日期)表示, 例如(M, 2)-周二出生的男孩,; (F, 7)-周日出生的女孩。
 *     用两个随机变量X={M,F} 和Y={1,2,3,4,5,6,7} 模拟孩子出生的情况, 
 *        (x1,y1) 和 (x2,y2)代表出生的两个孩子。变量sum记录总数,  two记录都是男孩。
 *        
 *        如果{ (x1,y1) , (x2,y2) } 有个周二出生的男孩,  sum加一
 *        如果{ (x1,y1) , (x2,y2) } 有个周二出生的男孩 并且都是男孩,  two加一
 *        
 *        最终的概率 = two/sum = 13/27 = 0.48148
 *        
 *  2) 朴素的分析
 *      两个小孩只可能四种情况(女, 女), (女, 男), (男, 女), (男, 男)
 *      + (女, 女) :  不满足条件, 放弃
 *      + (女, 男) :  男孩在周二出生, 女孩可能是7天中的一天出生. 7种可能情况
 *      + (男, 女) :  同上述类似， 7种可能情况
 *      + (男, 男) :  7x7=49种情况, 减去都不在周二的情况6x6=36， 一共7x7-6x6 = 13 种可能情况
 *      因此, 至少一个周二男孩的情况为:  7 + 7 + 13 = 27种;  满足两个男孩的情况 = 13种。
 *      概率 = 13/27 
 *      
 *   3) 贝叶斯公式
 *      事件A = {两个孩子, 至少一个周二男孩}
 *      事件B = {两个都是男孩}
 *      题目所求 = P(B/A) = P(AB)/P(A) = P(A/B)*P(B) / P(A)
 *      
 *      计算:
 *        P(B) = {两个都是男孩} = 1/4
 *        P(A/B) = {两个都是男孩时, 至少一个周二出生} = 1-(6/7)^2 = 13/49
 *        P(A) = {至少一个周二的男孩} = (1/4)*(1/7) + (1/4)*(1/7) + (1/4)*(13/49) = 27/196
 *      
 *      最终结果 P(B/A) = (13/49) * (1/4) / (27/196) = 13/27
 *      
 */

/**
 * 
 * @author admin
 *
 */
public class TwoBoy {

	/**
	 * 进行测试
	 * @param total - 总共测试的次数
	 */
	public static void TestTwoBoys(int total){
		
		int sum=0, two = 0;
		float p = 0.0f;
		
		//init random birth
		RandomBirth birth = new RandomBirth();
		
		for(int i=0;i<total;i++){
			TwoKids kids = new TwoKids(birth.genderBirth(),birth.dayBirth(), birth.genderBirth(),birth.dayBirth());
			if( kids.isAtleastBoyDay2() ){ //至少有一个周二的男孩
				sum ++;
				if( kids.isTwoBoy() ){ //两个都是男孩
					two ++;
				}
			}//end of if
		}//end of for
		
		p = (float)two/sum;
		
		System.out.println("Probability of two boys is : " + two + "/" + sum + " = " + p);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestTwoBoys(10000000);
	}

}

/**
 * 随机生成男孩/女孩 和出生的天数
 * 
 * @author admin
 *
 * */
class RandomBirth{
	
	//保证概率均等, 使用两个独立的随机数是比较正确的方法
	Random rgen = new Random();
	Random rday = new Random();
	
	/**
	 * boy or girl?
	 * @return M or F
	 */
	public char genderBirth(){
		int v = rgen.nextInt(2);  //[0,2)
		return (v==0)?'M':'F';
	}
	/**
	 * birthday of week
	 * @return 1,2,3,4,5,6,7
	 */
	public  int dayBirth(){
		int v = rday.nextInt(7);  //[0,7)
		return v+1; //[1,7]
	}
}

/**
 *  (gender, day) = (x,y)
 *  kids1 = (x1,y1) and kids2 = (x2,y2)
 *  
 * @author admin
 *
 */
class TwoKids{
	char x1,x2;
	int y1,y2;
	
	public TwoKids(char x1, int y1, char x2, int y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		
	}
	/**
	 * 两个都是男孩
	 * @return
	 */
	public boolean isTwoBoy(){
		return x1=='M' && x2 == 'M';
	}
	/**
	 * 至少一个周二的男孩
	 * @return
	 */
	public boolean isAtleastBoyDay2(){
		return (x1=='M' && y1==2) || (x2=='M' && y2==2);
	}
}
