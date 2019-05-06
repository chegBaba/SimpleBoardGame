package kalah;

public class GameBoard {
	Node head = null;
	
	static class Node
	{
		House data;
		Node next;
	};
	
	/*Function to insert a node at beginning of the circular linked list*/
	static Node push(Node head_ref, House data)
	{
		Node temp = head_ref;

		Node ptr1 = new Node();
		ptr1.data = new House(4);
		ptr1.next = head_ref;
		
		/* if linked list is not null
		 * then set the next of last node	 */
		if (head_ref != null)
		{
			while (temp.next != head_ref)
			{
				temp = temp.next;
			}
			temp.next = ptr1;
		}else {
			ptr1.next = ptr1;
		}
		head_ref = ptr1;
		return head_ref;
	}
	
	static void printList(Node head)
	{
		Node temp = head;
		if (head != null)
		{
			do {
				System.out.println(temp.data.getStoneNumb() + " " );
				temp = temp.next;
			}while (temp != head);
		}
	}
	
	public GameBoard() 
	{
		//initiate the game board with 12 houses 
		//each houses have four stones
		House m_house = new House(4);
		head = new Node();
		head.data = m_house;
		head.next = null;
		Store m_player1 = new Store("Player1");
		Store m_player2 = new Store("Player2");
		head = push(head,m_player1);
		for (int i=0; i<12; ++i)
		{
			//add stores to each side of the board
			if (i == 5)
				head = push(head,m_player2);
			head = push(head, m_house);	
		}
	}
	
	//Print current GameBoard
	public void showGameBoard()
	{
		System.out.println("+----+-------+-------+-------+-------+-------+-------+----+");
		System.out.println("| P2 | 6[ 4] | 5[ 4] | 4[ 4] | 3[ 4] | 2[ 4] | 1[ 4] |  0 |");
		System.out.println("|    |-------+-------+-------+-------+-------+-------|    |");
		System.out.println("|  0 | 1[ 4] | 2[ 4] | 3[ 4] | 4[ 4] | 5[ 4] | 6[ 4] | P1 |");
		System.out.println("+----+-------+-------+-------+-------+-------+-------+----+");
		System.out.println("Player 1's turn - Specify house number or 'q' to quit: ");
	}
	
	public int getStoneNumber(int houseNumber, int player)
	{
		int index = 0;
		Node temp = head;
		
		if (player == 1)
		{
			houseNumber += 6;
		}
		
		if (temp != null)
		{
			do { 
				++index;
				temp = temp.next;
			}while (index!=houseNumber);
		}
		
		if (temp !=null)
			return temp.data.getStoneNumb();
		System.out.print("temp is null");
		return -1;
	}
		
	
	public void moveStoneInSelectHouse(int houseNumber, int player)
	{
		int stones = getStoneNumber(houseNumber, player);
		Node temp = this.head;
		int index = 0;
		if (stones !=0)
		{
			for (int i=0; i<stones; ++i)
			{
				//keep relocate all stones till no stone left in hand
				//First, find the start point
				do {
					++index;
					temp = temp.next;
				}while(index!=houseNumber);
				//Second, in following house, each house add one stone
				temp.data.addStone();
				temp=temp.next;
			}
		}
		else {
			System.out.println("You have select a empty house!");
		}
	}
}
