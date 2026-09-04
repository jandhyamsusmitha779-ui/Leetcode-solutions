class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {

            int max = nums[0];

            // Maximum from 0 to i
            for (int j = 0; j <= i; j++) {
                max = Math.max(max, nums[j]);
            }

            int min = nums[i];

            // Minimum from i to n-1
            for (int j = i; j < n; j++) {
                min = Math.min(min, nums[j]);
            }

            // Check stability
            if (max - min <= k) {
                return i;
            }
        }

        return -1;
    }
}