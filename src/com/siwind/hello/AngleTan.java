package com.siwind.hello;

import sun.print.resources.serviceui;

public class AngleTan {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Test1();
	}

	public static void Test1() {
		TanValid();
		System.out.println("====");
		CosValid();
	}
	/**
	 * tan(a)*tan(b)*tan(c)*tan(d) =1
	 */
	public static void TanValid() {
		int i,a,b,c,d;
		double[] Tan=new double[90];
		double s;
		for(i=0;i<90;i++){
			Tan[i]= Math.tan(i*Math.PI/180.0);
		}
	
		i=0;
		for(a=1;a<90;a++){
			for(b=a;b<90 ;b++){
				if( b == (90-a)) continue;
				for(c=b;c<90 ;c++){
					if( c == (90-a) ||  c == (90-b)) continue;
					for(d=c;d<90;d++){
						if( d == (90-a) ||  d == (90-b) || d == (90-c) ) continue;
						if( 90-d < a) continue;  //already calculated! not equal because a+d != 90
						
						s = Tan[a]*Tan[b]*Tan[c]*Tan[d] -1;
						//s=Math.tan(a*Math.PI/180)*Math.tan(b*Math.PI/180)*Math.tan(c*Math.PI/180)*Math.tan(d*Math.PI/180)-1; 
						if( Math.abs(s)<1.0e-12){
							i++;
							System.out.println(i+"  (" + a+" "+b+" "+c+" "+d + ")     (" + (90-d) + " "+(90-c) + " " + (90-b) + " " + (90-a)+")");
						}
					}//end of d
				}//end of c
			}//end of b
		}//end of a
	}
	
	/**
	 * tan(a)*tan(b)*tan(c)*tan(d) =1
	 * which equals to 
	 */
	public static void CosValid() {
		int i,a,b,c,d;
		double[] Cos=new double[360];
		double s;
		for(i=0;i<359;i++){
			Cos[i] = Math.cos(i*Math.PI/180.0);
		}
		i=0;
		for(a=1;a<90;a++){
			for(b=a;b<90 ;b++){
				if( b == (90-a)) continue;
				for(c=b;c<90 ;c++){
					if( c == (90-a) ||  c == (90-b)) continue;
					for(d=c;d<90;d++){
						if( d == (90-a) ||  d == (90-b) || d == (90-c) ) continue;
						if( 90-d < a) continue;  //already calculated! not equal because a+d != 90
						
						// not cos, because cos(100)=-cos(80),
						s = Cos[Math.abs(a+b+c-d)]+Cos[Math.abs(a+b-c+d)]+Cos[Math.abs(a-b+c+d)]+Cos[Math.abs(-a+b+c+d)] ;
						
						if( Math.abs(s)<1.0e-12){
							i++;
							System.out.println(i+")  " + a+" "+b+" "+c+" "+d + " ---- " + (90-d) + " "+(90-c) + " " + (90-b) + " " + (90-a));
						}
					}//end of d
				}//end of c
			}//end of b
		}//end of a
	}
}
