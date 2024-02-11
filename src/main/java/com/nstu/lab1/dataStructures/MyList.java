package com.nstu.lab1.dataStructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyList<T> implements IList<T> {
    private Node<T> head;
    private Node<T> tail;
    private int length;

    private static class Node<T> {
        T data;
        Node<T> next;
        Node<T> prev;

        public Node(Object v) {
            this.data = (T) v;
            next = prev = null;
        }
    }

    public MyList() {

    }

    @JsonCreator
    public MyList(@JsonProperty("elements") List<T> elements) {
        for (T element : elements) {
            add(element);
        }
    }

    @JsonProperty("elements")
    public List<T> getElements() {
        List<T> elements = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            elements.add(current.data);
            current = current.next;
        }
        return elements;
    }

    public void addAll(MyList<?> loadedList) {
        loadedList.forEach(v -> this.add(v));
    }

    public T get(int index) {
        return getNode(index).data;
    }

    public Node<T> getHead() {
        return head;
    }

    public void remove(int index) {
        Node<T> tmp = getNode(index);
        if (tmp != head) {
            tmp.prev.next = tmp.next;
        } else {
            head = tmp.next;
        }
        if (tmp != tail) {
            tmp.next.prev = tmp.prev;
        } else {
            tail = tmp.prev;
        }
        tmp.next = tmp.prev = null;
        length--;
    }

    public int size() {
        return length;
    }

    public void clear() {
        head = null;
        tail = null;
        length = 0;
    }

    public void add(Object v) {
        if (head == null) {
            head = new Node<T>(v);
            tail = head;
            length++;
            return;
        }
        Node<T> newTail = new Node<T>(v);
        newTail.prev = tail;
        tail.next = newTail;
        tail = newTail;
        length++;
    }

    public void add(T data, int index) {
        Node<T> newNode = new Node<T>(data);

        /* if (length == 0) {
            add(data);
            return;
        } */

        if (index == 0) {
            if (head == null) {
                head = new Node<T>(data);
                tail = head;
                length++;
                return;
            }
            newNode.next = head;
            newNode.prev = null;
            head.prev = newNode;
            head = newNode;
            length++;
            return;
        }

        if (index == length){
            add(data);
            return;
        }

        Node<T> tmp = getNode(index);
        if (tmp != head) {
            tmp.prev.next = newNode;
            newNode.prev = tmp.prev;
        } else {
            head = newNode;
        }
        newNode.next = tmp;
        tmp.prev = newNode;
        length++;
    }

    public void forEach(Action<T> a) {
        Node<T> tmp = head;
        for (int i = 0; i < length; i++) {
            a.toDo(tmp.data);
            tmp = tmp.next;
        }
    }

    public List<T> toList() {
        List<T> list = new ArrayList<T>();
        Node<T> current = head;
        while (current != null) {
            list.add(current.data);
            current = current.next;
        }
        return list;
    }

    public void sort(Comparator<T> comparator) {
        head = mergeSort(head, comparator);
    }

    private Node<T> mergeSort(Node<T> h, Comparator<T> comparator) {
        if (h == null || h.next == null) {
            return h;
        }

        Node<T> middle = getMiddle(h);
        Node<T> middleNext = middle.next;

        middle.next = null;

        Node<T> left = mergeSort(h, comparator);

        Node<T> right = mergeSort(middleNext, comparator);

        return merge(left, right, comparator);
    }

    private Node<T> merge(Node<T> head11, Node<T> head22, Comparator<T> comparator) {
        Node<T> left = head11;
        Node<T> right = head22;
        Node<T> merged = new Node<T>(null);
        Node<T> temp = merged;
        while (left != null && right != null) {
            if (comparator.compare(left.data, right.data) < 0) {
                temp.next = left;
                left.prev = temp;
                left = left.next;
            } else {
                temp.next = right;
                right.prev = temp;
                right = right.next;
            }
            temp = temp.next;
        }
        while (left != null) {
            temp.next = left;
            left.prev = temp;
            left = left.next;
            temp = temp.next;
        }
        while (right != null) {
            temp.next = right;
            right.prev = temp;
            right = right.next;
            temp = temp.next;
            this.tail = temp;
        }
        return merged.next;
    }

    private Node<T> getMiddle(Node<T> h) {
        if (h == null)
            return null;
        Node<T> fast = h.next;
        Node<T> slow = h;

        while (fast != null) {
            fast = fast.next;
            if (fast != null) {
                slow = slow.next;
                fast = fast.next;
            }
        }
        return slow;
    }

    private Node<T> getNode(int index) {
        // if (index < 0 || index >= length)
        if (index == 0 || index >= length)
            return null;
        if (index < 0)
            throw new IndexOutOfBoundsException();
        Node<T> tmp = head;

        for (int i = 0; i < index; i++) {
            tmp = tmp.next;
        }
        return tmp;
    }

    public boolean isEmpty() {
        if (this.length == 0)
            return true;
        else
            return false;
    }

    public int getLength() {
        return length;
    }

}
