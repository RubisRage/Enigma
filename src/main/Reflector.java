package main;

class Reflector {
    private int[][] pairs;

    Reflector(int[][] pairs){
        this.pairs = pairs;
    }

    int findPair(int pair){

        for(int i = 0; i < 13; i++){
            if(pair == pairs[0][i]){
                return pairs[1][i];
            } else if(pair == pairs[1][i]) {
                return pairs[0][i];
            }
        }

        return -1;
    }
}
