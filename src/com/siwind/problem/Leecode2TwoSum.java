package com.siwind.problem;

/**
 * Created by wang on 17-3-10.
 */
public class Leecode2TwoSum {

    public static void main(String[] args){
        //int[] nums = new int[]{2,7,11,15};
        //int target = 9;

        int[] nums = new int[]{0,4,3,0};
        int target = 0;

        int[] res = new Leecode2TwoSum().twoSum(nums,target);

        System.out.println("[" + res[0] + ", " + res[1] + "]");
    }

    public int[] twoSum(int[] nums, int target) {
        int[] indices = new int[]{-1,-1};

        for( int i=0;i<nums.length;i++){

            for(int j=i+1;j<nums.length;j++){
                if( nums[i] + nums[j] == target ){
                    indices[0] = i;
                    indices[1] = j;
                    break;
                }
            }
        }
        if( indices[0] == -1 ){
            new IllegalArgumentException("No two sum solution");
        }
        return indices;
    }


}
