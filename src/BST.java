import java.util.Iterator;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Node<K, V>> {

    private Node<K, V> root;

    // A private inner class that represents a node in the binary search tree.
    public static class Node<K extends Comparable<K>, V> implements Comparable<Node<K, V>> {

        private K key;

        private V value;

        private Node<K, V> left;

        private Node<K, V> right;

        private int size;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
        @Override
        public int compareTo(Node<K, V> other) {
            return this.key.compareTo(other.key);
        }
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node<K, V> put(Node<K, V> x, K key, V value) {
        if (x == null) return new Node<>(key, value);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, value);
        else if (cmp > 0) x.right = put(x.right, key, value);
        else x.value = value;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public V get(K key) {
        Node<K, V> x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }

    public boolean contains(K key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size(root);
    }

    private int size(Node<K, V> x) {
        if (x == null) return 0;
        else return x.size;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node<K, V> delete(Node<K, V> x, K key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node<K, V> t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node<K, V> min(Node<K, V> x) {
        if (x.left == null) return x;
        else return min(x.left);
    }

    private Node<K, V> deleteMin(Node<K, V> x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    // Implementation of in-order traversal using an iterator.
    public Iterator<Node<K, V>> iterator() {
        return new BSTIterator<>(root);
    }

    public class BSTIterator<K extends Comparable<K>, V> implements Iterator<BST.Node<K, V>> {

        private Stack<Node<K, V>> stack;

        public BSTIterator(Node<K, V> root) {
            stack = new Stack<>();
            pushLeft(root);
        }

        private void pushLeft(Node<K, V> x) {
            while (x != null) {
                stack.push(x);
                x = x.left;
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public BST.Node<K, V> next() {
            Node<K, V> x = stack.pop();
            pushLeft(x.right);
            return x;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}