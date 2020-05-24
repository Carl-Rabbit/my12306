public class Price {
    public static double[] getPrice(String trainKind,Integer mileage){
        double[] res = new double[6];
        double price1 = 0;
        double price2 = 0;
        double price3 = 0;
        double price4 = 0;
        double price5 = 0;
        double price6 = 0;

        switch (trainKind.charAt(0)){
            case 'C':
            case 'D':
            case 'G':
            case 'P':
            case 'S':
            case 'Y':
                if (mileage<=200){
                    double p = 0.05681;
                    double mileage1 = mileage;
                    price6 = (mileage1*p)*(2+0.25+0.4)*1.5;
                    price4 = (mileage1*p)*(2+0.25+0.4)*1.5*1.5;
                    price2 = (mileage1*p)*(2+0.25+0.4)*1.5*3;
                    price1 = (mileage1*p)*(2+0.25+0.4+1.3)*1.5*1.5;
                    price3 = price1;
                    price5 = price1;
                }else if (mileage<=500){
                    double p = 0.052749;
                    double mileage1 = mileage-200;
                    double plus = 11.722;
                    price6 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*1.5;
                    price2 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*3;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.3)*1.5*1.5;
                    price3 = price1;
                    price5 = price1;
                }else if (mileage<=1000){
                    double p = 0.046888;
                    double mileage1 = mileage-500;
                    double plus = 27.5467;
                    price6 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*1.5;
                    price2 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*3;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.3)*1.5*1.5;
                    price3 = price1;
                    price5 = price1;
                }else if (mileage<=1500){
                    double p = 0.041027;
                    double mileage1 = mileage-1000;
                    double plus = 50.9907;
                    price6 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*1.5;
                    price2 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*3;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.3)*1.5*1.5;
                    price3 = price1;
                    price5 = price1;
                }else if (mileage<=2500){
                    double p = 0.035166;
                    double mileage1 = mileage-1500;
                    double plus = 71.5042;
                    price6 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*1.5;
                    price2 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*3;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.3)*1.5*1.5;
                    price3 = price1;
                    price5 = price1;
                }else {
                    double p = 0.029305;
                    double mileage1 = mileage-2500;
                    double plus = 106.6702;
                    price6 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*1.5;
                    price2 = (mileage1*p+plus)*(2+0.25+0.4)*1.5*3;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.3)*1.5*1.5;
                    price3 = price1;
                    price5 = price1;
                }

                break;
            case 'K':
            case 'T':
            case 'Z':
                if (mileage<=200){
                    double p = 0.05681;
                    double mileage1 = mileage;
                    price6 = (mileage1*p)*(1+0.25+0.4)*1.5;
                    price4 = (mileage1*p)*(2+0.25+0.4)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p)*(2+0.25+0.4+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p)*(1+0.25+0.4+1.2)*1.5+10;
                }else if (mileage<=500){
                    double p = 0.052749;
                    double mileage1 = mileage-200;
                    double plus = 11.722;
                    price6 = (mileage1*p+plus)*(1+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.4+1.2)*1.5+10;
                }else if (mileage<=1000){
                    double p = 0.046888;
                    double mileage1 = mileage-500;
                    double plus = 27.5467;
                    price6 = (mileage1*p+plus)*(1+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.4+1.2)*1.5+10;
                }else if (mileage<=1500){
                    double p = 0.041027;
                    double mileage1 = mileage-1000;
                    double plus = 50.9907;
                    price6 = (mileage1*p+plus)*(1+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.4+1.2)*1.5+10;
                }else if (mileage<=2500){
                    double p = 0.035166;
                    double mileage1 = mileage-1500;
                    double plus = 71.5042;
                    price6 = (mileage1*p+plus)*(1+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.4+1.2)*1.5+10;
                }else {
                    double p = 0.029305;
                    double mileage1 = mileage-2500;
                    double plus = 106.6702;
                    price6 = (mileage1*p+plus)*(1+0.25+0.4)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.4)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.4+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.4+1.2)*1.5+10;
                }
                break;
            default:
                if (mileage<=200){
                    double p = 0.05681;
                    double mileage1 = mileage;
                    price6 = (mileage1*p)*(1+0.25+0.2)*1.5;
                    price4 = (mileage1*p)*(2+0.25+0.2)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p)*(2+0.25+0.2+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p)*(1+0.25+0.2+1.2)*1.5+10;
                }else if (mileage<=500){
                    double p = 0.052749;
                    double mileage1 = mileage-200;
                    double plus = 11.722;
                    price6 = (mileage1*p+plus)*(1+0.25+0.2)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.2)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.2+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.2+1.2)*1.5+10;
                }else if (mileage<=1000){
                    double p = 0.046888;
                    double mileage1 = mileage-500;
                    double plus = 27.5467;
                    price6 = (mileage1*p+plus)*(1+0.25+0.2)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.2)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.2+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.2+1.2)*1.5+10;
                }else if (mileage<=1500){
                    double p = 0.041027;
                    double mileage1 = mileage-1000;
                    double plus = 50.9907;
                    price6 = (mileage1*p+plus)*(1+0.25+0.2)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.2)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.2+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.2+1.2)*1.5+10;
                }else if (mileage<=2500){
                    double p = 0.035166;
                    double mileage1 = mileage-1500;
                    double plus = 71.5042;
                    price6 = (mileage1*p+plus)*(1+0.25+0.2)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.2)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.2+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.2+1.2)*1.5+10;
                }else {
                    double p = 0.029305;
                    double mileage1 = mileage-2500;
                    double plus = 106.6702;
                    price6 = (mileage1*p+plus)*(1+0.25+0.2)*1.5;
                    price4 = (mileage1*p+plus)*(2+0.25+0.2)*1.5;
                    price2 = price4;
                    price1 = (mileage1*p+plus)*(2+0.25+0.2+1.8)*1.5+10;
                    price3 = price1;
                    price5 = (mileage1*p+plus)*(1+0.25+0.2+1.2)*1.5+10;
                }
                break;
        }
        res[0] = price1;
        res[0] = price2;
        res[0] = price3;
        res[0] = price4;
        res[0] = price5;
        res[0] = price6;
        return res;
    }
}
