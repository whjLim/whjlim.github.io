public class Solution{
	/*
	merger
	sword - > 1
	二维数组查找
	1 343 828 762
	*/
	public boolean Find(int target, int[][] array){
		if(null == array || array.length == 0)return false;
		
		int n = array.length, m = array[0].length;
		int x = n-1, y = 0;
		while(x>=0 && y < m){
			
			if(array[x][y] == target){
				return true;
			}
			
			if(array[x][y] > target){
				x--;
			}else{
				y++;
			}
		}
		return false;
	}
	/*
	sword - > 2
	替换空格
	*/
	public String replaceSpace(StringBuffer str){
		int numspace = 0;
		int lens = str.length();
		for(int i = 0; i < lens; i++){
			if(str.charAt(i) == ' '){
				numspace += 1;
			}
		}
		int newLens = lens+numspace*2;
		str.setLength(newLens);
		for(int i = lens-1, j = newLens-1; i >= 0; i--){
			if(str.charAt(i) == ' '){
				str.setCharAt(j--, '0');
				str.setCharAt(j--, '2');
				str.setCharAt(j--, '%');
				//str.replace(j-2,j+1,"%20");
				//j-=3;
			}else{
				str.setCharAt(j--,str.charAt(i));
			}
		}
		return str.toString();
	}
	/*
	sword - > 3
	从尾到头打印链表
	public class ListNode{
		int val;
		ListNode next = null;
		ListNode(int val){
			this.val = val;
		}
	}
	*/
	public ArrayList<Integer> printListFromTailTOHead(ListNode listNode){
		//方案1, 先读取数据，在翻转
		//方案2, 先翻转链表，在读取数据
		if(listNode == null)return new ArrayList<Integer>();
		List<Integer> list = new ArrayList<Integer>();
		ListNode pre = null;
		while(listNode.next != null){
			ListNode next = listNode.next;
			listNode.next = pre;
			pre = listNode;
			listNode = next;
		}
		while(listNode != null){
			list.add(listNode.val);
			listNode = listNode.next;
		}
		return list;
	}
	/*
	sword -> 4
	重建二叉树
	前序+中序
	前：{1,2,4,7,3,5,6,8}
	中：{4,7,2,1,5,3,8,6}
	前序：谁是根
	中序：左右子数的分布，注意数量
	*/
	
	int index;
	int treeNodeNums;
	
	public int findInIndex(int[] in, int target){
		for(int i = 0, len = in.length; i < len; i++){
			if(in[i] == target){
				return i;
			}
		}
		return -1;
	}
	public TreeNode reConstructBinaryTree(int[] pre, int[] in){
		
		index = -1;
		treeNodeNums = pre.length;
		TreeNode binaryTree = buildTree(pre, in, 0, pre.length-1);
		return binaryTree;
	}
	public TreeNode buildTree(int[] pre, int[] in, int l, int r){
		if(l <= r){
			index++;
			TreeNode root = new TreeNode(pre[index]);
			int mid = findInIndex(in, pre[index]);
			root.left = buildTree(pre, in, l, mid-1);
			root.right = buildTree(pre, in, mid+1, r);
			return root;
		}
		return null;
	}
	/*
	sword -> 5
	双栈成队
	*/
	Stack<Integer> stack1 = new Stack<Integer>();
	Stack<Integer> stack2 = new Stack<Integer>();
	public void push(int node){
		while(!stack2.isEmpty()){
			stack1.push(stack2.pop());
		}
		stack2.push(node);
		while(!stack1.isEmpty()){
			stack2.push(stack1.pop());
		}
	}
	public int pop(){
		return stack2.pop();
	}
	/*
	sword -> 6
	旋转数组的最小数字 唯一行
	3 4 5 1 2
	*/
	public int minNumberUbRotateArray(int[] array){
		if(null == array || array.length == 0)return 0;
		int left = 0, right = array.length-1;
		while(left <= right){
			while(left < right && array[left+1] == array[left]){
				left++;
			}
			while(rigt > left && array[right-1] == array[right]){
				right--;
			}
			if(left != right && array[left] == array[right]){
				right--;
			}
			int mid = left + (right-left)/2;
			if(array[right] < array[left]){
				left = mid+1;
			}else{
				right = mid-1;
			}
		}
		return array[left];
	}
	
	/*
	sword ->
	二叉搜索树的后序遍历序列
	
	hit: 后续+中序创建树，要反过来，先右子树，在左子树
	*/
	int index;
	public int findIndex(int[] in, int target){
		
		for(int i = 0, lens = in.length; i < lens; i++){
			if(in[i] == target){
				return i;
			}
		}
		return -1;
		
	}
	public boolean VerifySequenceOfBST(int[] sequence){
		if(null == sequence || sequence.length == 0)return true;
		//排个序，得到中序
		int[] in = sequence.clone();
		Arrays.sort(in);
		index = sequence.length;
		return buildTree(in, sequence, 0, sequence.length-1);
	}
	public boolean buildTree(int[] in, int[] sequence, int l, int r){
		if(l <= r){
			index--;
			int root = sequence[index];
			int mid = findIndex(in, root);
			int right = r-mid;
			int left = mid - l;
			//右边比较大
			for(int i = 0; i < right; i++){
				if(sequence[index-i-1] < root){
					return false;
				}
			}
			//左边比较小
			for(int i = 0; i < left; i++){
				if(sequence[index-right-i-1] > root){
					return false;
				}
			}
			return buildTree(in, sequence, l, mid-1) && buildTree(in, sequence, mid+1, r);
		}
		return true;
	}
	/*
	sword -> 
	二叉树中和为某一值的路径
	15:31 2019/8/5
	路径为根节点到叶子节点
	*/
	
	ArrayList<ArrayList<Integer>>  res = new ArrayList<ArrayList<Integer>>();
	ArrayList<Integer> list = new ArrayList<Integer>();
	public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target){
		dfs(root, target, 0);
		for(int i = 0, len = res.size(); i < len; i++){
			int max = res.get(i).size();
			int index = i;
			for(int j = i + 1; j < len; j++){
				if(res.get(j).size() > max){
					max = res.get(j).size();
					index = j;
				}
			}
			if(index != j){
				list = res.get(i);
				res.set(i, res.get(index));
				res.set(index, list);
			}
		}
		return res;
	}
	
	public void dfs(TreeNode root, int target, int sum){
		if(sum > target || root == null){
			return;
		}else{
			sum += root.val;
			list.add(root.val);
			if(sum == target && root.left == null && root.right == null){
				ArrayList<Integer> ans = new ArrayList<Integer>();
				for(Integer ing : list){
					ans.add(ing);
				}
				res.add(ans);
				return;
			}
			if(root.left != null && sum <= target){
				dfs(root.left, target, sum);
				list.reomve(list.size()-1);
			}
			if(root.right != null && sum <= target){
				dfs(root.right, target, sum);
				list.reomve(list.size()-1);
			}
			
		}
	}
	
	/*
	sword -> 
	复杂链表的复制
	16:19 2019/8/5
	1. 先复制总链，在根据每个节点的随机值去链
	*/
	public class RandomListNode {
		int label;
		RandomListNode next = null;
		RandomListNode random = null;
		
		RandomListNode(int label){
			this.label = label;
		}
		
	}
	public RandomListNode Clone(RandomListNode pHead){
		if(null == pHead)return null;
		RandomListNode cloneHead = new RandomListNode(pHead.label);
		RandomListNode p = cloneHead;
		RandomListNode q = pHead.next;
		//1. 复制主链
		while(q != null){
			RandomListNode node = new RandomListNode(q.label);
			p.next = node;
			p = node;
			q = q.next;
		}
		//2. 处理随机链 -- 遍历链，确定链到哪个节点
		q = pHead;
		p = cloneHead;
		while(q != null){
			RandomListNode random = q.random;
			if(random != null){
				RandomListNode find = pHead;
				RandomListNode cloneFinde = cloneHead;
				while(find != random){
					find = find.next;
					cloneFinde = cloneFinde.next;
				}
				p.random = cloneFinde;
			}
			p = p.next;
			q = q.next;
		}
		return cloneHead;
	}
	
	/*
	sword -> 
	二叉搜索树与双向链表
	== 将二叉搜索树转成一个排序的双向链表，不能创建任何新的节点
	16:41 2019/8/5
	*/
	TreeNode pre = null;
	TreeNode head = null;
	public TreeNode Convert(TreeNode pRootOfTree){
		treeToLink(pRootOfTree);
		return head;
	}
	public void treeToLink(TreeNode pRootOfTree){
		if(pRootOfTree != null){
			treeToLink(pRootOfTree.left);
			if(pre != null){
				pre.right = pRootOfTree;
				pRootOfTree.left = pre;
			}
			if(pre == null){
				head = pRootOfTree;
			}
			pre = pRootOfTree;
			treeToLink(pRootOfTree.right);
		}
	}
	/*
	sword ->
	字符串的排列
	17:12 2019/8/5
	*/
	Map<String, Integer> map = new HashMap<String, Integer>();
	ArrayList<String>  res = new ArrayList<String>();
	public ArrayList<String> Permutation(String str){
		 if(null == str || str.length() == 0)return res;
		char[] chr = str.toCharArray();
		for(int i = 0, len = chr.length; i < len; i++){
			char min = chr[i];
			int index = i;
			for(int j = i+1; j < len; j++){
				if(chr[j] < min){
					min = chr[j];
					index = j;
				}
			}
			if(index != i){
				char temp = chr[i];
				chr[i] = chr[index];
				chr[index] = temp;
			}
		}
		char[] build = new char[chr.length];
		boolean[] visit = new boolean[chr.length];
		buildString(chr, build, visit, 0, chr.length)
		return res;
	}
	public void buildString(char[] chr, char[] build, boolean[] visit,int index, int lens){
		
		if(index == lens){
			String str = String.valueOf(build);
			if(!map.containsKey(str)){
				map.put(str, 1);
				res.add(str);
			}
			return;
		}
		
		for(int i = 0; i < lens; i++){
			if(!visit[i]){
				visit[i] = true;
				build[index] = chr[i];
				buildString(chr, build, visit, index+1, lens);
				visit[i] = false;
			}
		}
	}
	
	17:26 2019/8/5 休息
	20:14 2019/8/5 开工
	/*
	sword -> 1
	数组中出现次数超过一半的数字
	两两相撞，最后剩下的就是了吧
	1 2 3 2 4 2 5 2 3
	出现错误--如果没有的话
	一定有才是正确的
	=====有坑
	*/
	public int MoreThanHalfNum_Solution(int [] array) {
        if(null == array || array.length == 0)return 0;
		int nums = 1;
		int half = array[0];
		for(int i = 1, len = array.length; i < len; i++){
			if(array[i] == nums){
				nums++;
			}else{
				nums--;
				if(nums)
			}
		}
		if(index < 0)return 0;
		return array[index];
    }
	/*
	sword -> 2
	最小的K个数
	20:46 2019/8/5
	思路： 先求第K小，则前面的
	注意：快排的写法
	*/
	 public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {

        ArrayList<Integer> res = new ArrayList<Integer>();
        int l = 0, r = input.length-1;
        while(l <= r){
            int mid = parament(input, l, r);
            if(mid+1 == k){
                for(int i = 0; i < k; i++){
                    res.add(input[i]);
                }
                break;
            }
            if(mid+1 > k){ // 下标没处理好，坑一波
                r = mid-1;
            }else{
                l = mid+1;
            }
        }

        return res;
    }
    public int parament(int[] input, int l, int r){

        int temp = input[l];
        while(l < r){
            while(r > l && input[r] >= input[l]) r--;
            input[l] = input[r];
            while(l < r && input[l] <= input[r]) l++;
            input[r] = input[l];
        }
        input[l] = temp;
        return l;
    }
	
	/*
	sword -> 3
	连续子数组的最大和
	21:29 2019/8/5
	有毒 ->
	*/
	public int FindGreatestSumOfSubArray(int[] array) {
		if(array == null || array.length == 0)return 0;
		int sum = array[0];
		int max = array[0];
		for(int i = 1, len = array.length; i < len; i++){
			if(sum <= 0){
				sum = array[i];
			}else{
				sum += array[i];
			}
			max = Math.max(sum, max);
		}
		return max;
    }
	/*
	9:02 2019/8/6 新的一天，新的征途
	*/
	/*
	sword --> 1
	整数中1出现的次数(1-n)
	9:27 2019/8/6
	思路：
	各位数为abcdefg,
	每位有0-a,0-b,0-c,0-d ... 0-g的数可以选
	注意： 当高未满时，低位有0-9的方案可以选
	所以
	g = 1 有 abcdef +1 或者 abcdef 种 (g==0)
	f = 1 有 (abcde)f(g) 10*(abcde+1) 或 10*(abcde)
	e = 1 有 (abcd)e(fg) 100*(abcd+1) 
	....
	a = 1 有 (bcdefg+1) 或(1000000)种
	以此类推
	--------
	21345
	19475 错
	18821 对
    
	5 = 2134+1
	4 = 214*10
	3 = 22*100
	1 = 3*1000 (错误--01 可以到1000，1只能到345+1)
	2 
	21:07 2019/8/6
	hit：
	   各个位之间没有考虑清楚：
	    = 0, =1要特别考虑的
	*/
	public int NumberOf1Between1AndN_Solution(int n){
		int res = 0;
		int t = n;
		int after = 1;
		while(t >= 10){
			int befor = t / 10;
			int mod = t % 10;
			if(mod == 0){
				res += befor * after;
			}else if(mod == 1){
				res += (n % after + 1);
			}else{
				res += (befor + 1) * after;
			}
			t = befor;
			after *= 10;
		}
		if(t == 1){
			res += n-after+1; //
		}else{
			res += after;
		}
		return res;
	}
	/*
	sword --> 2
	把数组排成最小的数
	10:52 2019/8/6
	11:24 2019/8/6
	[3,32,321] ->变成321323
	332321
	332132
	323321
	323213
	321332
	321323
	
	按 特殊规则 排序
	//频繁创建字符串？
	int cmd(int a, int b){
		String as = String.valueOf(a);
		String bs = String.valueOf(b);
		String ab = as + bs;
		String ba = bs + as;
		if(ab.compareTo(ba) < 0){
			return true;
		}else{
			return false;
		}
	}
	int cmd(int a, int b){
		String as = String.valueOf(a);
		String bs = String.valueOf(b);
		String ab = as + bs;
		String ba = bs + as;
		if(ab.compareTo(ba) < 0){
			return true;
		}else{
			return false;
		}
	}
	21:29 2019/8/6
	快排写错了，比较不是左右比较，是跟分割元素比较
	*/
	int cmd(String as, String bs){

		String ab = as + bs;
		String ba = bs + as;
		
		return ab.compareTo(ba); //0相等  < 0 小  >0 大于 
	}
	
	
	
	public String PrintMinNumber(int[] numbers){
		if(null == numbers || numbers.length == 0)return ""; //
		String[] strNums = new String[numbers.length];
		for(int i = 0, len = numbers.length; i < len; i++){
			strNums[i] = String.valueOf(numbers[i]);
		}
		quickSort(strNums, 0, strNums.length-1);
		StringBuilder sb = new StringBuilder();
		for(int i = 0, len = strNums.length(); i < len; i++){
			sb.append(strNums[i]);
		}
		return ab.toString();
		
	}
	//手写个快排序吧，复习个
	public void quickSort(String[] strs, int l, int r){
		if(l < r){
			int mid = parament(strs, l, r);
			quickSort(strs, l, mid-1);
			quickSort(strs, mid+1, r);
		}
	}
	public int parament(String[] strs, int l, int r){
		
		String temp = strs[l];
		while(l < r){
			while(r > l && cmd(strs[r], strs[l]) >= 0)r--;
			strs[l] = strs[r];
			
			while(l < r && cmd(strs[l], strs[r]) <= 0)l++;
			strs[r] = strs[l];
			
		}
		strs[l] = temp;
		return l;
	}
	/*
	sword -> 3
	丑数
	12:01 2019/8/6
	质因子只含有2,3,5
	6,8是 14不是1是第一个
	1
	5 3 2
	0 0 1
	0 1 0
	0 0 2
	1 0 0
	1   1  = 1         5 3 2    1
	2   2  = 2         0 0 1	2	1个2              = f(1)*2
	3   3  = 3         0 1 0	3 	1个3              = f(1)*3
	4   4  = 2*2       0 0 2	4	2个2              = f(2)*2
	5   5  = 5         1 0 0	5	1个5              = f(1)*5  -1结束
	6   6  = 2*3       0 1 1	6	1个3，1个2        = f(2)*3 = f(3)*2
	7   8  = 2*2*2     0 0 3	7	3个2              = f(4)*2
	8   9  = 3*3       0 2 0	8	2个3              = f(3)*3
	9   10 = 2*5       1 0 1	9	1个5,1个2         = f(2)*5  =f(5)*2   -2结束
	10  12 = 2*2*3     0 1 2	10	1个3,2个2         = f(4)*3 = f(6)*2
	11  15 = 3*5       1 1 0	11 	1个5,1个3         = f(3)*5 = f(5)*3 -3结束
	12  16 = 2*2*2*2   0 0 4	12	4个2              = f(7)*2;
	13  18 = 2*3*3     0 2 1	13	2个3，1个2        = f(6)*3 = f(8)*2;
	14  20 = 2*2*5     1 0 2	14	1个5,2个2         = f(4)*5 =f(9)*2 -4结束
	15  24 = 2*2*2*3   0 1 3	15	1个3,3个2         = f(7)*3 = f(10)*2
	16  25 = 5*5       2 0 0	16	2个5              = f(5)*5 -5结束
	17  27 = 3*3*3     0 3 0	17	3个3              = f(8)*3
	18  30 = 2*3*5     1 1 1	18	1个5，1个3,1个2   = f(6)*5 =f(11)*2 = f(9)*3 -6结束
	19  32 = 2*2*2*2*2 0 0 5	19	5个2              = f(12)*2
	2 3
	2*2 = 4
	3*3 = 9
	2 3 5
    2 < 3 < 2*2 < 5 < 2*3 < 2*2*2 < 2*5 < 2*2*3 < 3*5 < 2*2*2*2 < 2*3*3
	1 * (2-3-22-5)
	2 * (3,22,2)
	3 * ()
	
	14:55 2019/8/6 思考了半天，觉得是跟2,3,5有关，也有想到后面的数都是前面的数去根{2,3,5}顺序乘下来
	卡了蛮久的，想不顺通，看了下博客有提到顺序，一点就通，试着撸一发代码
	21:32 2019/8/6
	忽略了0的处理
	*/
	public int min(int a, int b, int c){
		if(b < a){
			a = b;
		}
		if(c < a){
			a = c;
		}
		return a;
	}
	public int GetUglyNumber_Solution(int index){
		int five = 0, three = 0, two = 0;
		int[] ugly = new int[index+1];
		ugly[0] = 1;
		for(int i = 1; i < index; i++){
			
			int next_two = ugly[two]*2;
			int next_three = ugly[three]*3;
			int next_five = ugly[five]*5;
			ugly[i] = min(next_two, next_three, next_five);
			if(next_two == ugly[i]){
				two++;
			}
			if(next_three == ugly[i]){
				three++;
			}
			if(next_five == ugly[i]){
				five++;
			}
		}
		return ugly[index-1];
	}
	/*
	sword -> 4
	14:58 2019/8/6  
	第一个只出现一次的字符
	思路： 直接暴力了吧，统计字符次数和第一个出现的位置，扫描第一个出现的位置就行了
	21:35 2019/8/6
	各种细节没处理
	*/
	public int FirstNotRepeatingChar(String str){
		int[] charNums = new int[256];
		int[] first = new int[256];
		for(int i = 0, len = str.length; i < len; i++){
			if(charNums[s.charAt(i)] == 0){
				first[s.charAt(i)] = i;
			}
			charNums[s.charAt(i)]++;
		}
		int index = str.length;
		for(int i = 'a'; i <= 'z'; i++){
			if(charNums[i] == 1 && first[i] < index){
				index = first[i];
			}
		}
		for(int i = 'A'; i <= 'Z'; i++){
			if(charNums[i] == 1 && first[i] < index){
				index = first[i];
			}
		}
		if(index == str.length){
			index = -1;
		}
		return index;
	}
	/*
	sword -> 5
	数组中的逆序对
	前面的数大于后面的数 就是一个逆序对 % 1000000007
	15:17 2019/8/6
	16:08 2019/8/6
	思路：
		之前好像有做过一道输出逆序数的，大概就是用归并排序的思路进行
		
		暴力遍历 n*n，只能%75吧(10^5)
		
		归并排序，两边有序对比一下就能计算了
		(a b c d)(e f g h)
		归并的时候(a b c d)有序 (e f g h)有序
		那么逆序数就倒过来，如果 d h 逆序， d g-e 也逆序
	*/
	int MOD = 1000000007;
	int res = 0;
	public int InversePairs(int[] array){
		if(null == 0 || array.length == 0)return 0;
		temp = new int[array.length];
		mergerSort(array, 0, array.length-1);
		return res;
	}
	/*
	复习一波归并排序
	Arrays.copyOfRange();
	*/
	public void mergerSort(int[] array, int l, int r){
		
		if(l < r){
			int mid = l + (r-l)/2;
			mergerSort(array, l, mid);   // l - mid 一段
			mergerSort(array, mid+1, r); //mid+1 - r 一段
			//合并的时候统计逆序数
			/*
			(l - mid )(mid+1 -- r)
			*/
			for(int i = mid; i >= l; i--){
				for(int j = r; j > mid; j--){
					if(array[i] > array[j]){
						res = (res+(j-mid))%MOD;
						break;
					}
				}
			}
			merge(array, l, mid, r);
			
		}
	}
	int[] temp; //排序辅助数组
	public void merger(int[] array, int l, int mid, int r){
		int left = l;
		int right = mid+1;
		int index = 0;
		while(left <= mid && right <= r){
			if(array[left] < array[right]){
				temp[index++] = array[left++];
			}else{
				temp[index++] = array[rigth++];
			}
		}
		while(left <= mid)temp[index++] = array[left++];
		while(right <= r)temp[index++] = array[rigth++];
		for(left = l, right = 0; right < index; left++, right++){
			array[left] = temp[right];
		}
	}
	
	/*
	sword -> 6
	两个链表的第一个公共结点
	16:11 2019/8/6
	17:15 2019/8/6
	思路： 
		来个头尾相连--形成环 === 问题转换环形链表的入环点？
		A: 链表起点
		B：环入口
		C：环内相遇点 
		指针慢走走了：A-B-C
		指针快走走了：A-B-C-B-C
		A-B = x
		B-C = y
		C-B = z
		最后就是2(x+y) = x+y+z+y => x = z
		A-B == C-B
		
	21:47 2019/8/6  还要解开，不然就死循环了
	22:16 2019/8/6 脑壳子疼，wa了无数发，flag一下，回头再看看
	*/
	public ListNode LoopNode(ListNode head){
		if(null == head)return null;
		ListNode slow = head;
		ListNode fast = head;
		while(fast != null && slow != null){
			slow = slow.next;
			fast = fast.next.next;
			if(fast == slow){
				break;
			}
		}
		slow = head;
		while(slow != fast){
			slow = slow.next;
			fast = fast.next;
		}
		return slow;
	}
	public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2){
		if(pHead1 == null || pHead2 == null)return null;
		ListNode head = pHead1;
		while(head.next != null){
			head = head.next;
		}
		head.next = pHead2;
		ListNode com = LoopNode(pHead1);
		head = pHead1;
		while(head.next != pHead2){
			head = head.next;
		}
		head.next = null;
		return com;
	}
	
	/*
	sword -> 7
	数字在排序数组中出现的次数
	思路：
		折半查找，前后统计
		17:17 2019/8/6
		17:22 2019/8/6
	*/
	public int GetNumberOfK(int[] array, int k){
		int l = 0, r = array.length-1;
		int valueIndex = -1;
		while(l <= r){
			int mid = l + (r-l)/2;
			if(array[mid] == k){
				valueIndex = mid;
				break;
			}
			if(array[mid] < k){
				l = mid+1;
			}else{
				r = mid-1;
			}
		}
		int res = 0;
		if(valueIndex != -1){
			res = 1;
			int i = valueIndex-1;
			while(i >= 0 && array[i] == array[valueIndex])res++;
			i = valueIndex+1;
			while(i < array.length && array[i] == array[valueIndex])res++;
		}
		return res;
	}
	/*
	sword -> 8
	二叉树的深度
	19:27 2019/8/6
	19:29 2019/8/6
	没啥好说的，直接撸
	*/
	public int TreeDepth(TreeNode root) {
        if(null == root)return 0;
		return Math.max(TreeDepth(root.left), TreeDepth(root.right))+1;
    }
	/*
	sword -> 9
	平衡二叉树
	判断是否为平衡二叉树
	19:39 2019/8/6
	19:48 2019/8/6
	思路：
		1. 先判断是否为二叉排序树
		2. 在判断高度差是否符合平衡
		? 需要排序吗? 不需要，不是平衡二叉排序树
	*/
	public boolean isSortTree(){
		
	}
	boolean balance = true;
	int abs(int x){
		if(x < 0)x = -x;
		return x;
	}
	public int TreeDepth(TreeNode root) {
        if(null == root)return 0;
		int left = TreeDepth(root.left);
		int right = TreeDepth(root.right);
		if(abs(left-right) > 1)balance = false;
		return Math.max(left, right)+1;
    }
	public boolean IsBalanced_Solution(TreeNode root) {
        TreeDepth(root);
		return balance;
    }
	/*
	sword -> 10
	数组中只出现一次的数字
	19:51 2019/8/6
	20:13 2019/8/6
	两个数字之外，其他的数字都出现了两次
	思路：	
	    一个数 异或 他本身 = 0 (00=0,11=0,10=01=1)就0
		出现两次：x^x = 0
		所以数组一遍异或运算之后，值为两个不同的值的异或  = a^b;
        两个不同 -> a^b != 0;
	*/
	 public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        int ab = 0;// 0^x = x
		for(int i = 0, len = array.length; i < len; i++){
			ab ^= array[i];
		}
		//找到第一个 ab 不为1的位置
		int first = ab & (~(ab-1));
		num1[0] = 0;
		num2[0] = 0;
		for(int i = 0, len = array.length; i < len; i++){
			if( (array[i] & first) == 0){  // (位运算记得加())
				num1[0] ^= array[i];
			}else{
				num2[0] ^= array[i];
			}
		}
    }
	/*
	和为S的连续正数序列
	22:17 2019/8/6
	思路：
		[a-b]的和 为 a*(b-a+1) + ((b-a+1)(b-a))/2 = sum
		2a*(b-a+1) +(b-a+1)(b-a) = 2sum
		(b-a+1)*(2a+b-a) = 2sum
		(b-a+1)*(b+a) = 2sum
		b2-a2+b+a = 2sum
		b2+b=2sum+a2-a
		9~16
		a = 9
		b = 16
	22:50 2019/8/6
	复习了一下韦达定理
	
	*/
	public ArrayList<ArrayList<Integer> > FindContinuousSequence(int sum) {
       
	   ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer>>();
	   for(int i = 1, len = sum/2; i <= len; i++){
		   int c = 2*sum+i*i-i;
		   int sqn = 1+4*c;
		   int c2 = (int)Math.sqrt(sqn);
		   if(c2*c2 == sqn && (c2 & 1) == 1){
			   c2 = (c2-1)>>1;
			   ArrayList<Integer> list = new ArrayList<Integer>();
			   for(int j = i; j <= c2; j++){
				   list.add(i);
			   }
			   res.add(list);
		   }
	   }
	   return res;
	   
    }
	/*
	22:53 2019/8/6 在来一道简单的，整整自信心
	左旋转字符串
	23:05 2019/8/6 前旋转 -- 后旋转 -- 一起旋转
	*/
	public String LeftRotateString(String str,int n) {
        if(null == str || str.length() == 0)return str;
		n = n % str.length();
		if(n == 0)return str;
		char[] chars = str.toCharArray();
		rotation(chars, 0, n-1);
		rotation(chars, n, chars.length-1);
		rotation(chars, 0, chars.length-1);
		return String.valueOf(chars);
    }
	public void rotation(char[] chars, int l, int r){
		while(l < r){
			char t = chars[l];
			chars[l] = chars[r];
			chars[r] = t;
			l++;
			r--;
		}
	}
	/*
	sword -> 
	0:12 2019/8/7 这一题结束睡觉
	机器人的运动范围
	不能进入行坐标和列坐标的数位之和大于k的格子
	0:43 2019/8/7
	*/
	int[] fx = new int[]{-1,1,0,0};
	int[] fy = new int[]{0,0,-1,1};
	int res;
	boolean[][] visit;
	int[] numssum;
	int n, m;
	int limit;
	void dfs(int x, int y){
		for(int i = 0; i < 4; i++){
			int xi = x + fx[i];
			int yi = y + fy[i];
			if(xi < 0 || yi < 0 || xi >= n || yi >= m || visit[xi][yi] ||(numssum[xi]+numssum[yi]) > limit)continue;
			visit[xi][yi] = true;
			res++;
			dfs(xi,yi);
		}
	}
	
	public int movingCount(int threshold, int rows, int cols)
    {
		if(threshold < 0)return 0;
        visit = new boolean[rows][cols];
		n = rows;
		m = cols;
		limit = threshold;
		numssum = new int[Math.max(rows, cols)];
		for(int i = 0, len = Math.max(rows, cols); i < len; i++){
			int s = 0;
			int t = i;
			while(t > 0){
				s += t%10;
				t = t/10;
			}
			numssum[i] = s;
		}
		visit[0][0] = true;
		res = 1;
		dfs(0,0);
		return res;
    }
	/*
	19:54 2019/8/7 搬数据的一天，赶紧开工刷刷题
	sword -> 1
	翻转单词顺序列
	19:57 2019/8/7
	“student. a am I”
	“I am a student.
	20:03 2019/8/7
	*/
	 public String ReverseSentence(String str) {
		if(null == str || str.trim().equals(""))return str;
        String[] strs = str.split(" ");
		StringBuilder sb = new StringBuilder();
		sb.append(strs[strs.length-1]);
		for(int i = strs.length-2; i >= 0; i--){
			sb.append(" ");
			sb.append(strs[i]);
		}
		return sb.toString();
    }
	/*
	sword-> 2
	扑克牌顺子
	20:06 2019/8/7
	思路：
		简单排个序，前后比较差值
		[0,3,2,6,4]
		0 2 3 4 6
		20:20 2019/8/7
	*/
	public boolean isContinuous(int [] numbers) {
		if(null == numbers || numbers.length == 0)return false;
		Arrays.sort(numbers);
		int zeroIndex = 0;
		while(numbers[zeroIndex] == 0){
			zeroIndex++;
		}
		for(int i = zeroIndex+1, len = numbers.length; i < len; i++){
			if(numbers[i] - numbers[i-1] > 1){
				zeroIndex  = zeroIndex - (numbers[i] - numbers[i-1] - 1);
				if(zeroIndex < 0)
					return false;
			}else if(numbers[i] == numbers[i-1])
				return false;
		}
		return true;
    }
	/*
	sword -> 3
	把字符串转换成整数
	20:29 2019/8/7
	20:36 2019/8/7
	*/
	public int StrToInt(String str) {
        if(null == str || str.length() == 0)return 0;
		int index = 0;
		if(str.charAt(index) == '+' || str.charAt(index) == '-'){
			index++;
		}
		int res = 0;
		for(int i = index, len = str.length(); i < len; i++){
			if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				res = res*10 + (str.charAt(i)-'0');
			}else{
				return 0;
			}
		}
		if(index > 0 && str.charAt(index-1) == '-'){
			res = -res;
		}
		return res;
    }
	/*
	sword -> 4
	长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的
	20:46 2019/8/7
	21:02 2019/8/7
	思路： 
		震惊，居然直接暴力
	
	*/
	public boolean duplicate(int numbers[],int length,int [] duplication) {
        if(length <= 0)return false;
        boolean[] visit = new boolean[length];
        for(int i = 0; i < length; i++){
            if(visit[numbers[i]]){
               duplication[0] = numbers[i];
                return true;
            }
            visit[numbers[i]] = true;
        }
        return false;
    }
	
	/*
	sword -> 5
	构建乘积数组
	给定一个数组A[0,1,...,n-1],
	请构建一个数组B[0,1,...,n-1],
	其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。
	不能使用除法。
	21:06 2019/8/7
	思路： 
		a[0,1,2,3,4,5,6,7,8,9]
		b[0] = a[1,2,3,4,5,6,7,8,9]
		b[1] = a[0,2,3,4,5,6,7,8,9]
		b[2] = a[0,1,3,4,5,6,7,8,9]
		b[3] = a[0,1,2,4,5,6,7,8,9]
		b[4] = a[0,1,2,3,5,6,7,8,9]
		b[5] = a[0,1,2,3,4,6,7,8,9]
		b[6] = a[0,1,2,3,4,5,7,8,9]
		b[7] = a[0,1,2,3,4,5,6,8,9]
		b[8] = a[0,1,2,3,4,5,6,7,9]
		b[9] = a[0,1,2,3,4,5,6,7,8]
		看起来就是 不要一口气算一个数，大家都慢慢的数，最后在一起算完
		也就是遍历数组，对于下标 i，除了b[i],都乘上a[i];
	21:45 2019/8/7 
	暴力，没超时，能优化吗？
	1:05 2019/8/8
	有的
	两个辅助数组
	befor[i] 存储0-(i-1)的乘机
	after[i] 存储(i+1)-底的乘机
	B[i] = befor[i]*after[i];
	三趟遍历O(n)
	*/
	public int[] multiply(int[] A) {
		if(A== null || A.length == 0)return A;
        int[] B = new int[A.length];
		
		for(int i = 0, len = A.length; i < len; i++){
			B[i] = 1;
		}
		for(int i = 0, len = A.length; i < len; i++){
			for(int j = 0; j < len; j++){
				if(i != j){
					B[j] *= A[i];
				}
			}
		}
		return B;
    }
	
	/*
	表示数值的字符串
	请实现一个函数用来判断字符串是否表示数值（包括整数和小数）。
	例如，字符串"+100","5e2","-123","3.1416"和"-1E-16"都表示数值。
	但是"12e","1a3.14","1.2.3","+-5"和"12e+4.3"都不是。
	22:01 2019/8/7
	思路：
		1. 第一个是否正负号
		2. 是否有e
		3. e之后是否有正负号
		4. e之后是否有小数点
		5. e之后得有数
		6. e之前不能是.
		22:48 2019/8/7
	*/
	public boolean isNumeric(char[] str) {
        if(null == str || str.length == 0)return false;
        int index = 0; //

        int len = str.length;
        boolean flage = false;
        int point = 0; //小数点

        if(str[index] == '+' || str[index] == '-')
            index++;
        for(int i = index; i < len; i++){
            if(str[i] == 'e' || str[i] == 'E'){
				if(i > 0 && str[i-1] == '.')
					return false;
                if(flage)return false;
                flage = true;
            }else if(str[i] == '.'){
                if(!flage && point == 0){
                    point = 1;
                }else{
                    return false;
                }
            }else if(str[i] == '+' || str[i] == '-'){
                if(str[i-1] != 'e' && str[i-1] != 'E'){
                    return false;
                }
            }else if(str[i] < '0' || str[i] > '9'){
                return false;
            }
        }
        return true;
		
    }
	/*
	22:53 2019/8/7
	尼玛，怎么那么多乱七八糟的题目，看到眼睛酸，腰疼，行吧行吧，先把剩下的秒题秒了
	*/
	/*
	删除链表中重复的结点
	在一个排序的链表中，存在重复的结点，
	请删除该链表中重复的结点，
	重复的结点不保留，返回链表头指针。 
	例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
	22:54 2019/8/7 
	思路:
		
	23:10 2019/8/7
	*/
	public ListNode deleteDuplication(ListNode pHead)
    {
		ListNode head = pHead;
		ListNode pre = null;
		while(pHead != null && pHead.next != null){//下一个不是空才有判断的意义
			if(pHead.val == pHead.next.val){
				while(pHead.next != null && pHead.val == pHead.next.val){
					pHead = pHead.next;
				}
				if(pre != null){
					pre.next = pHead.next;
				}else{
					head = pHead.next;
				}
			}else{
				pre = pHead;
			}
			pHead = pHead.next;
		}
		return head;
    }
	/*
	23:11 2019/8/7
	二叉树的下一个结点
	
	给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点并且返回。
	注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
	
	思路：
		中序遍历的下一个节点？
		下一个节点，我还没遍历过，我怎么知道下一个节点是啥
		思路反转，记录上一个节点，
		那么，如果我的上一个节点是pNode, 那么我就是pNode的下一个节点
    =============================
	   但是这里还给了父亲节点的信息，那么
	   中序遍历，如果我有右孩子，那么我的下一个节点就是我的右孩子的左子树点
	             如果我没有右孩子，那么我的下一个节点就是祖先节点
				     如果我是父节点的左孩子，我父节点就是我下一个节点
					 如果我是父节点的右孩子，
						 如果我的父节点是左孩子，父节点的父节点就是我下一个节点
						 如果我的父节点是右孩子，父节点的父节点就要递归上去
	23:43 2019/8/7
	舒服，一发入魂
	*/
	class TreeLinkNode {
    int val;
    TreeLinkNode left = null;
    TreeLinkNode right = null;
    TreeLinkNode next = null;

    TreeLinkNode(int val) {
        this.val = val;
    }
	 public TreeLinkNode GetNext(TreeLinkNode pNode)
    {
        if(pNode== null)return null;
		if(pNode.right != null){//我有右孩子
			TreeLinkNode next = pNode.right;
			while(next.left != null) next = next.left;
			return next;
		}else{
			return NextIsParent(pNode);
		}
    }
	/*
	如果我没有右孩子，那么我的下一个节点就是祖先节点
				     如果我是父节点的左孩子，我父节点就是我下一个节点
					 如果我是父节点的右孩子，
						 如果我的父节点是左孩子，父节点的父节点就是我下一个节点
						 如果我的父节点是右孩子，父节点的父节点就要递归上去
	*/
	public TreeLinkNode NextIsParent(TreeLinkNode pNode){
		if(pNode.next == null)return null;
		if(pNode.next.left == pNode){//如果我是父节点的左孩子，我父节点就是我下一个节点
			return pNode.next;
		}else{
			return NextIsParent(pNode.next);
		}
	}
	/*
	按之字形顺序打印二叉树
	23:55 2019/8/7
	0:26 2019/8/8
	*/
	public ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
		if(pRoot == null)return new ArrayList<ArrayList<Integer> >();
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer> >();
		Stack<TreeNode> stack = new Stack<TreeNode>();
		Queue<TreeNode> que = new LinkedList<TreeNode>();
		int zf = 0;
		que.offer(pRoot);
		TreeNode temp = null;
		while(!que.isEmpty()||!stack.isEmpty()){
			
			ArrayList<Integer> list = new ArrayList<Integer>();
			if((zf & 1) ==  0){//从队列拿，左右入栈
                Queue<TreeNode> rotation = new LinkedList<TreeNode>();
				while(!que.isEmpty()){
					temp = que.poll();
					list.add(temp.val);
					if(temp.left != null){
						rotation.add(temp.left);
					}
					if(temp.right != null){
						rotation.add(temp.right);
					}
				}
                while(!rotation.isEmpty()){
					stack.push(rotation.poll());
				}
                
			}else{//从栈拿，右到左入栈在入队
				Stack<TreeNode> rotation = new Stack<TreeNode>();
				while(!stack.isEmpty()){
					temp =  stack.pop();
					list.add(temp.val);
					if(temp.right != null){
						rotation.push(temp.right);
					}
					if(temp.left != null){
						rotation.push(temp.left);
					}
				}
				while(!rotation.isEmpty()){
					que.offer(rotation.pop());
				}
			}
			res.add(list);
			zf++;
		}
        return res;
    }
	/*
	把二叉树打印成多行
	0:27 2019/8/8
	0:30 2019/8/8
	复制上一题修改一下
	*/
	public ArrayList<ArrayList<Integer> > Print(TreeNode pRoot) {
		if(pRoot == null)return new ArrayList<ArrayList<Integer> >();
		ArrayList<ArrayList<Integer>> res = new ArrayList<ArrayList<Integer> >();
		Queue<TreeNode> que = new LinkedList<TreeNode>();
		int zf = 0;
		que.offer(pRoot);
		TreeNode temp = null;
		while(!que.isEmpty()){
			
			ArrayList<Integer> list = new ArrayList<Integer>();
			Queue<TreeNode> rotation = new LinkedList<TreeNode>();
			while(!que.isEmpty()){
				temp = que.poll();
				list.add(temp.val);
				if(temp.left != null){
					rotation.add(temp.left);
				}
				if(temp.right != null){
					rotation.add(temp.right);
				}
			}
			que = rotation;
			res.add(list);
		}
        return res;
    }
	/*
	对称的二叉树
	请实现一个函数，用来判断一颗二叉树是不是对称的。
	注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
	0:31 2019/8/8
	0:35 2019/8/8
	*/
	boolean isSymmetrical(TreeNode pRoot)
    {
        if(pRoot == null)return true;
		return isSymmetrical(pRoot.left,pRoot.right);
    }
	boolean isSymmetrical(TreeNode left, TreeNode right){
		if(left == null && right == null)return true;
		if(left == null || right == null)return false;
		if(left.val == right.val){
			return isSymmetrical(left.left, right.right) && isSymmetrical(left.right, right.left);
		}else{
			return false;
		}
	}
	// 19:38 2019/8/8 立秋了，时间过得真快
	/*
	sword -> 1
	19:39 2019/8/8
	请实现两个函数，分别用来序列化和反序列化二叉树
	思路：
		有点懵，序列化是遍历？
		--输入是啥，不知道，汗
	*/
	String Serialize(TreeNode root) {
        if(null == root)return null;
		Queue<TreeNode> que = new LinkedList<TreeNode>();
		que.offer(root);
		StringBuilder sb = new StringBuilder();
		while(!que.isEmpty()){
			TreeNode t = que.poll();
			if(t != null){
				sb.append(t.val);
				que.offer(t.left);
				que.offer(t.right);
			}else{
				sb.append("#");
			}	
		}
		return sb.toString();
	}
    TreeNode Deserialize(String str) {
       if(null == str || str.length() == 0)return null;
	   char[] cs = str.toCharArray();
	   int index = 0;
	   TreeNode root = new TreeNode(cs[index++]-'0');
	   Queue<TreeNode> que = new LinkedList<TreeNode>();
        que.offer(root);
	   while(!que.isEmpty()){
		   TreeNode t = que.poll();
		   if(index < cs.length && cs[index] != '#'){
			   t.left = new TreeNode(cs[index++]-'0');
			   que.offer(t.left);
		   }
		   if(index < cs.length && cs[index] != '#'){
			   t.right = new TreeNode(cs[index++]-'0');
			   que.offer(t.right);
		   }
	   }
	   return root;
	}
	
	/*
	sword -> 2
	二叉搜索树的第k个结点
	*/
	TreeNode knode = null;
	int index = 0;
	TreeNode KthNode(TreeNode pRoot, int k)
    {
		LDR(pRoot, k);
		return knode;
    }
	void LDR(TreeNode pRoot, int k)
    {
        if(null != pRoot && index < k){
			LDR(pRoot.left,k);
			index++;
			if(index == k){
				knode = pRoot;
			}
			LDR(pRoot.right,k);
		}
    }
	/*
	sword -> 3
	滑动窗口的最大值
	21:14 2019/8/8
	2,3,4,2,6,2,5,1
	max()
	0:00 2019/8/9
	静下心来，好好体会优秀的代码
	*/
	public ArrayList<Integer> maxInWindows(int [] num, int size)
    {
		if(num == null || size <= 0 || size > num.length)return new ArrayList<Integer>();
        ArrayDeque<Integer>  deque = new ArrayDeque<Integer>();
        int max = num[0];
        deque.offerLast(0);
        for(int i = 1; i < size; i++){

            while(!deque.isEmpty() && num[deque.peekLast()] < num[i])
                deque.pollLast();
            deque.offerLast(i);
            if(num[i] > max)max = num[i];
        }
        ArrayList<Integer> res = new ArrayList<Integer>();
        res.add(max);
        for(int i = size, len = num.length; i < len; i++){
            if(deque.peekFirst() == i-size){
                deque.pollFirst();
            }
            while(!deque.isEmpty() && num[deque.peekLast()] < num[i])
                deque.pollLast();
            deque.offerLast(i);
            res.add(num[deque.peekFirst()]);
        }
        return res;
    }
}