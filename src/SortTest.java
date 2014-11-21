import java.util.Arrays;


class DataWrap implements Comparable<DataWrap>
{
	int data;
	String flag;
	
	public DataWrap(int data, String flag)
	{
		this.data = data;
		this.flag = flag;
	}
	
	public String toString()
	{
		return data + flag;
	}

	@Override
	public int compareTo(DataWrap dw) 
	{
		return this.data > dw.data ? 1 : (this.data == dw.data ? 0 : -1);
	}
}

/*
 * long distance swapping causes sorting unstable, 
 * while neighbor swapping makes sorting stable
 */

public class SortTest {

	// Stability: NO
	public static void selectSort(DataWrap[] data)
	{
		System.out.println("Start select sorting ...");
		
		// for i from beginning(0) to second last, select smallest one 
		// among subsequent ones and then place it in position i
		for (int i=0; i<data.length-1; ++i)
		{
			int index = i;
			// for j from next (i+1) to last, find smallest one (assign its position to index)
			for (int j=i+1; j< data.length; ++j)
			{
				if (data[j].compareTo(data[index]) < 0)
				{
					index = j;
				}
			}
			
			// swap them if the i-th element is not the smallest one of this round  
			if (index != i)
			{
				DataWrap tmp = data[i];
				data[i] = data[index];
				data[index] = tmp;
			}
			
			System.out.println(java.util.Arrays.toString(data));
		}	
	}
	
	public static void buildMaxHeap(DataWrap[] data, int lastIndex)
	{
		// for each non-leaf node, do swapping if its child is bigger than it, 
		// finally the root node becomes the biggest one
		for (int i=(lastIndex-1)/2; i>=0; --i)
		{
			// if the node has right child
			if (2*i+2 <= lastIndex)
			{ 
				int biggerChildIndex = 
				  (data[2*i+2].compareTo(data[2*i+1]) > 0) ? 2*i+2 : 2*i+1;
				
				// if the bigger child is bigger than current non-leaf node, swap them 
				if (data[biggerChildIndex].compareTo(data[i]) > 0)
				{
					swap(data, i, biggerChildIndex);
				}
			}
			
			// if the node only has left child
			if (2*i+1 <= lastIndex)
			{
				// if the left child is bigger than current non-leaf node, swap them
				if (data[2*i+1].compareTo(data[i]) > 0)
				{
					swap(data, i, 2*i+1);
				}
			}
		}
	}
	
	public static void swap(DataWrap[] data, int i, int j)
	{
		DataWrap tmp = data[i];
		data[i] = data[j];
		data[j] = tmp;
	}
	
	// Stability: NO
	public static void heapSort(DataWrap[] data)
	{
		System.out.println("Start heap sorting ...");
		
		// for "last index" from last to 1, build max heap, swap heap root with heap last element. i.e.
		// place biggest one to the last position, then 2nd biggest one to the 2nd last position, and so on ... 
		for (int i=data.length-1; i>0; --i)
		{
			buildMaxHeap(data, i);
			
			swap(data, 0 , i);
			
			System.out.println(java.util.Arrays.toString(data));
		}	
	}
	
	// Stability: YES
	public static void bubbleSort(DataWrap[] data)
	{
		System.out.println("Start bubble sorting ...");
		
		// for i from second last to 0, "bubble" the biggest one of this round to position i+1
		for (int i=data.length-2; i>=0; --i)
		{
			boolean swapped = false;
			// for j from beginning to i, compare current (j) with next (j+1), swap them if current is bigger
			for (int j=0; j<=i; ++j)
			{
				if (data[j].compareTo(data[j+1]) > 0)
				{
					DataWrap tmp = data[j];
					data[j] = data[j+1];
					data[j+1] = tmp;
					
					swapped = true;
				}
			}
			
			System.out.println(java.util.Arrays.toString(data));
			
			// finish earlier if no swapping happens in this round
			if (!swapped)
			{
				break;
			}
		}	
	}
	
	// Stability: NO
	public static void quickSort(DataWrap[] data)
	{
		System.out.println("Start quick sorting ...");
		
		// call helper method by taking the entire array into account
		quickSortHelper(data, 0, data.length-1);
	}
	
	public static void quickSortHelper(DataWrap[] data, int start, int end)
	{
		if (start < end)
		{
			// take the first element as base
			int base = start;
			
			// step i from "start/base + 1", to find bigger one
			int i = start+1;
			// step j from end, to find smaller one
			int j = end;
			
			while (true)
			{
				// step forward until find the one bigger than base, or reach end
				while (i < end && data[i].compareTo(data[base]) <= 0)
				{
					i++;
				}
				
				// step backward until find the one less than base, or reach start
				// NOTE: reach start (NOT start+1), to ensure data[j]<=data[base] 
				// and therefore the below swapping will behave correctly 
				while (j > start && data[j].compareTo(data[base]) >= 0)
				{
					j--;
				}
				
				if (i<j)
				{
					// i before j: swap the 2 elements, then continue stepping 
					swap(data, i, j);
				}
				else
				{	
					// i after/is j: already cover all elements, stop stepping
					break;
				}
			}
				
			// if data[j] < data[base], swap them to put the base element to the position 
			// where left part is smaller than base element and right part is bigger than it
			// if j reaches start (data[j] == data[base]), swapping them does no harm 
			swap(data, base, j);
			
			System.out.println(java.util.Arrays.toString(data));
			
			// sort the left part to position j
			quickSortHelper(data, start, j-1);
			// sort the right part to position j
			quickSortHelper(data, j+1, end);		
		} 
	}
	
	// Stability: YES
	public static void insertSort(DataWrap[] data)
	{
		System.out.println("Start insert sorting ...");
		
		// for i from 1 (2nd element) to last, insert current element to the previous sorted elements 
		for (int i=1; i<data.length; ++i)
		{			
			// current element is in-between previous elements
			if (data[i].compareTo(data[i-1]) < 0)
			{
				// backup current (i-th) element
				DataWrap tmp = data[i];
				
				int j = i-1;
				// j from i-1 to 0, if the one is bigger than current one, move it to next (j+1)
				// NOTE: if the one equal to current one, stop moving. the current one is placed after 
				// the equal one (ensure stability).
				for (;j>=0 && data[j].compareTo(tmp) > 0; --j)
				{
					data[j+1] = data[j];
				}
				
				// move current element back to j+1 position (j-th element is the first one found to be smaller) 
				data[j+1] = tmp;
			}
			
			System.out.println(java.util.Arrays.toString(data));
		}
	}
	
	// Stability: NO - extended insertSort
	public static void shellSort(DataWrap[] data)
	{
		System.out.println("Start shell sorting ...");
		
		// h = h * 3 + 1;
		int h = 1;
		final int factor = 3; // usually use 3
		while (h < data.length / factor)
		{
			h = h * factor + 1;
		}
		
		while (h > 0)	// last iteration: h == 1
		{
			System.out.println("=========h = " + h + "=========");
			// i from h-th element to last, insert current element to the previous sorted elements 
			// (elements with distance h are considered as the same group)
			for (int i=h; i<data.length; ++i)	// NOTE: ++i here (not i+=h)
			{			
				if (data[i].compareTo(data[i-h]) < 0)
				{
					DataWrap tmp = data[i];
					
					int j = i-h;
					// equal case, insert after (rather than insert before) to reduce movement
					for (; j>=0 && data[j].compareTo(tmp) > 0 ; j-=h)
					{
						data[j+h] = data[j];
					}
					
					data[j+h] = tmp;
				}
				
				System.out.println(java.util.Arrays.toString(data));
			}
			
			h = (h - 1) / factor;
		}
	}
		
	// Stability: YES
	public static void mergeSort(DataWrap[] data)
	{
		System.out.println("Start merge sorting ...");
		
		// call helper method by taking the entire array into account
		mergeSortHelper(data, 0, data.length - 1);
	}
	
	public static void mergeSortHelper(DataWrap[] data, int start, int end)
	{
		if (start < end)
		{
			int center = (start + end) / 2;
			// merge sort the left part
			mergeSortHelper(data, start, center);
			// merge sort the right part
			mergeSortHelper(data, center + 1, end);
			
			// merge the two parts together
			merge(data, start, center, end);
		}
	}
	
	public static void merge(DataWrap[] data, int start, int center, int end)
	{
		// create a temporary array to store the merged resul		t
		DataWrap[] tempArray = new DataWrap[end - start + 1];
		
		int index  = 0;
		// i for left part and j for right part
		int i=start, j=center+1;	
		while (i<=center && j<=end)
		{
			// always pick the smaller one from the two parts and copy it in temporary array 
			// NOTE: if left part element equals to right part element, take the first part 
			// element first (ensure stability)
			if (data[i].compareTo(data[j]) <= 0 )
			{
				tempArray[index++] = data[i++];
			}
			else
			{
				tempArray[index++] = data[j++];
			}
		}
		
		// left part traversed, right part remained
		if (i > center)
		{
			// copy the remaining ones to temporary array
			while (j<=end)
			{
				tempArray[index++] = data[j++];
			}
		}
		
		// right part traversed, left part remained
		if (j > end)
		{
			// copy the remaining ones to temporary array
			while (i<=center)
			{
				tempArray[index++] = data[i++];
			}
		}
		
		// copy temporary array back to original array
		for (int k=0; k< tempArray.length; ++k)
		{
			data[start+k] = tempArray[k];
		}
	}
	
	// Stability: YES
	public static void bucketSort(DataWrap[] data, int min, int max)
	{
		System.out.println("Start bucket sorting ...");
		
		// create a bucket array which stores the count for corresponding original element
		int[] buckets = new int[max - min + 1];
		
		for (int i=0; i<data.length; ++i)
		{
			// increase the count (which means the number of elements that go to this bucket)
			buckets[data[i].data - min]++;
		}
		
		for (int i=1; i < buckets.length; ++i)
		{
			// calculate number of elements that go to 
			// current bucket and all previous buckets
			buckets[i] = buckets[i] + buckets[i-1];
		}
		
		// backup the original array
		DataWrap[] tempArray = new DataWrap[data.length];
		System.arraycopy(data, 0, tempArray, 0, data.length);
		
		// for k from end to begin, traverse the temporary array elements
		// NOTE: reversed traverse to make the sorting "stable"
		for (int k=data.length-1; k>=0; --k)
		{
			//int count = buckets[tempArray[k].data - min];
			//count--;
			//buckets[tempArray[k].data - min] = count;
			
			// locate the bucket, get the count, calculate the index (decrease the count)
			int index = --buckets[tempArray[k].data - min];
			// copy current temporary array element to original array in calculated position
			data[index] = tempArray[k];
		}
	}
	
	// Stability: YES
	public static void radixSort(int[] data, int radix, int d)
	{
		System.out.println("Start radix sorting ...");
		
		int[] buckets = new int[radix];	// in this case: radix is 10 (0 ~ 9)
		int[] tempArray = new int[data.length];                        
		int rate = 1;	// in this case: rate is 1, 10, 100, ...
		
		// for each sub-key (d is number of keys), do the bucket sorting respectively ...
		// the final sorting result is stable, since we do it from "LSD" to "MSD"
		for (int j=0; j<d; ++j)
		{
			Arrays.fill(buckets, 0);
			for (int i=0; i<data.length; ++i)
			{
				// calculate the subkey according to rate and radix
				int subkey = (data[i] / rate) % radix;
				buckets[subkey]++;
			}
			
			for (int i=1; i < buckets.length; ++i)
			{
				buckets[i] = buckets[i] + buckets[i-1];
			}
			
			System.arraycopy(data, 0, tempArray, 0, data.length);
			
			for (int k=data.length-1; k>=0; --k)
			{
				int subkey = (tempArray[k] / rate) % radix;
				data[--buckets[subkey]] = tempArray[k];
			}
			
			System.out.println("sub-key sorting for rate " + rate + ":"
					+ java.util.Arrays.toString(data));
			
			rate*=10;	
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataWrap[] data = {
			new DataWrap(9, ""),
			new DataWrap(-16, ""),
			new DataWrap(21, "*"),
			new DataWrap(23, ""),
			new DataWrap(-30, ""),
			new DataWrap(-49, ""),
			new DataWrap(21, ""),
			new DataWrap(30, "*"),
			new DataWrap(30, "")
		};		
		
		System.out.println("Before Sorting: ");
		System.out.println(java.util.Arrays.toString(data));
		
		// select sort: time O(n^2), space O(1), not stable
		//selectSort(data);
		
		// heap sort: time O(n*log2 n) ?, space O(1), not stable
		//heapSort(data);
		
		// bubble sort: time good O(n) bad O(n^2), space O(1), stable
		//bubbleSort(data);
		
		// quick sort: time O good O(n*log2 n) bad O(n^2), space O(n*log2 n), not stable
		//quickSort(data);
		
		// insert sort: time O(n^2), space O(1), stable
		//insertSort(data);
		
		// shell sort: time O(n^ 3/2) ~ O(n^ 7/6), not stable
		//shellSort(data);

		// merge sort: time O(n*log2 n), space O(n), stable
		mergeSort(data);		
		
		System.out.println("After Sorting: ");
		System.out.println(java.util.Arrays.toString(data));

		// for bucket sorting
		DataWrap[] data1 = {
			new DataWrap(9, ""),
			new DataWrap(5, ""),
			new DataWrap(-1, ""),
			new DataWrap(8, ""),
			new DataWrap(5, "*"),
			new DataWrap(7, ""),
			new DataWrap(3, ""),
			new DataWrap(-3, ""),
			new DataWrap(1, ""),
			new DataWrap(3, "*")
		};
		
		System.out.println();
		System.out.println("Before Sorting: ");
		System.out.println(java.util.Arrays.toString(data1));

		// bucket sort: time O(n), space O(n), stable
		// need to pass in "min" and "max" value 
		bucketSort(data1, -5, 10);
		
		System.out.println("After Sorting: ");
		System.out.println(java.util.Arrays.toString(data1));

		//
		int[] data2 = {1100, 192, 221, 23, 12, 13};
		System.out.println("Before Sorting: ");
		System.out.println(java.util.Arrays.toString(data2));
		
		// radix sort:  multiple subkey sorting
		radixSort(data2, 10, 4);
		System.out.println("After Sorting: ");
		System.out.println(java.util.Arrays.toString(data2));
	}

}
