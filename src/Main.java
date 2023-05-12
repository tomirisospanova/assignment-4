public class Main {
    public static void main(String[] args) {
        BST<String, Integer> bst = new BST<>();
        bst.put("Almaty", 1);
        bst.put("Astana", 2);
        bst.put("Taraz", 3);
        bst.put("Shymkent", 4);
        bst.put("Oskemen", 5);

        // Test get method
        System.out.println(bst.get("Taraz")); // Output: 3

        // Test delete method
        bst.delete("Shymkent");
        System.out.println(bst.size()); // Output: 4

        // Test iterator
        for (BST.Node<String, Integer> node : bst) {
            System.out.println(node.getKey() + ": " + node.getValue());
        }
    }
}