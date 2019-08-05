public class Solution{
	/*
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
	sword -> 4
	连续子数组的最大和
	21:29 2019/8/5
	有毒 ->
	*/
	
	
}