class HashTableProject {
	public static void main(String[] args){
		MyHashTable t = new MyHashTable();
		t.insert("fooo", 2);
		t.insert("fooo1", 2);
		t.insert("fooobar", 2);
		t.insert("75", "Mike");
		t.insert("boo", 3.14);
		
		System.out.println(t.showAll());
	}
}