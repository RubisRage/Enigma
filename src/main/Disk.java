package main;

class Disk {

    private Node root;


    private static class Node{
        private Node next;
        private Node connection;
        private String name;

        private Node(String name){
            this.name = name;
            next = null;
            connection = null;
        }

        @Override
        public String toString(){
            return name + " " + connection.name + " ";
        }
    }


    Disk(String name){
        root = new Node(name + "(0)");
        Node actual = root;

        for(int i = 0; i < 25; i++){
            actual.next = new Node(name + "(" + (i+1) + ")");
            actual = actual.next;
        }


        actual.next = root;
    }

    void linkWiring(Disk disk, int[] wiring, int initialPosition){
        Node thisActual = root;
        Node thatActual = disk.root;

        for(int i = 0; i < 26; i++){
            for(int j = 0; j < wiring[initialPosition]; j++){
                thatActual = thatActual.next;
            }
            thisActual.connection = thatActual;
            thatActual.connection = thisActual;
            thatActual = disk.root;
            thisActual = thisActual.next;
            initialPosition++;
            if(initialPosition == wiring.length) initialPosition = 0;
        }
    }

    int getOutput(String name){
        Node actual = root;
        int letter = 0;

        while(!actual.name.equals(name)){
            letter++;
            actual = actual.next;
        }

        return letter;
    }

    String getInput(int letter){

        Node actual = root;

        for(int i = 0; i < letter; i++){
            actual = actual.next;
        }

        return actual.connection.name;
    }

    void rotate(){
        root = root.next;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder("{");
        Node actual = root;

        for(int i = 0; i < 26; i++){
            s.append(i).append(": (").append(actual.name).append(" <-> ").append(actual.connection.name).append(") | ");
            actual = actual.next;
        }

        return s.toString();
    }
}
