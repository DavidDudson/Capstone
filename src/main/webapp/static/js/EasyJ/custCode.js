/**
 * Created by adhoulih on 10/08/2015.
 */

function codeHeader(){

    var header = "public class testBot {\n\
                    \n\
                    Grid gameGrid;\n\
                    int nextMoveCoord[] = new int[2]\
                    \n\
                    testBot(Grid grid){\n\
                        this.gameGrid = grid;\n\
                    }\n\
                    \n\
                    public static int[] runMove(){\n\
                        //        start of injected code \n";
    return header;

}

function codeFooter() {
    var footer = "//        end of injectedCode\n\
                        return nextMoveCoord;\n\
                        \n\
                        }\n\
                        \n\
                    }";
    return footer;
}


