import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: zane
 * Date: 4/24/12
 * Time: 11:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class PixelConverter {

    public static int colorsList[]={0,12727841,2472996,11382055,4796129,13842643,3390408,13356237,8487811,16529695,3270434,15395875,5780479,16332280,1372400,15330283};


    private static int getColorScore(int color,int red,int blue,int green)
    {
             return Math.abs(red - (color >> 16) & 0xFF) + Math.abs(green - (color>> 8) & 0xFF) + Math.abs(blue - (color & 0xFF));
    }

    public static int getTranslatedColor(int red,int blue,int green)
    {
         int topScore=Integer.MAX_VALUE;
         int score;
         int colorIndex=0;
          for (int i=0; i < colorsList.length; i++){
              score=getColorScore(colorsList[i],red,blue,green);
              if(score < topScore)
              {
                   colorIndex=i;
                   topScore=score;
              }
          }
         return colorsList[colorIndex];
    }




}
