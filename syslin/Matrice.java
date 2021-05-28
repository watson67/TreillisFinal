package syslin ;

/**
 *
 * @author thiba
 */
public class Matrice {
    //--------------- partie 1.1

    //Définition des attributs de la classe

    int nbrLig; //nombre de ligne
    int nbrCol; //nombre de colonne
    double[][] coeffs; //matrice
    
    //Constructeur
    public Matrice(int nl, int nc)
    {
        this.nbrLig= nl;
        this.nbrCol= nc;
        this.coeffs=  new double[nl][nc];
    }
    
    public static void afficheTableau(Matrice m, int nl, int nc){
        for(int i=0; i<nl; i++){
             for(int j=0;j<nc;j++){
                    System.out.print(m.coeffs[i][j]+" ");
        }
        System.out.println();
    }
        
    }
    public double epsilon_pivot=10^-8;

   //Méthode .get
    public int getnbrCol(){
        return this.nbrCol;
    }
    public int getnbrLig(){
        return this.nbrLig;
    }
    public double[][] getcoeff(){
    double coeff[][]= this.coeffs;
    
    return coeff;
    }

    
    public double get(int lig, int col) {
        return this.coeffs[lig][col];
    }

   //Méthode .set
   public void set(int i,int j ,double k){
       this.coeffs[i][j]=k;
       
   } 
   
   public static String formatDoubleFixe(double x) {
    return String.format("%+4.2E", x);
}
   public static String formatDouble(double x) {
    return formatDoubleFixe(x);
}
//méthode toString
    public String toString() {
    
    String res = "";
    for (int i = 0; i < nbrLig; i++) {
        res = res + "[";
        for (int j = 0; j < nbrCol; j++) {
            res = res + formatDouble(this.get(i, j));
            if (j < nbrCol - 1) {
                res = res + " ";
            }
        }
        res = res + "]\n";
    }
    return res;
}
   //Méthode identité
    static Matrice identite(int n){
        Matrice id = new Matrice (n,n) ;
            for(int i = 0;i<n; i++){
               
                id.coeffs[i][i]=1;
            }
            return id;
    }
    //--------------- partie 1.2

    public Matrice MatriceNulle(int nbrLig, int nbrCol) {
        this.nbrLig = nbrLig;
        this.nbrCol = nbrCol;
        this.coeffs = new double[nbrLig][nbrCol];
        // il est inutile en java d'initialiser explicitement les coeffs à
        // zéro : java initialise automatiquement à zéro les tableaux
        // numériques.
        // !!! ce ne serait pas forcément le cas dans d'autres langages (ex C)
        return this;
    }
    
    //Méthode matTest1
    static Matrice matTest1(int n){
    Matrice m = new Matrice (n, n);
    double coeff=0;
    for (int i=0; i<n ; i++){
        for(int j=0; j<n; j++){
            m.coeffs[i][j]=coeff;
            coeff+=1;
    //équivalent à coeff=coeff+1
        }
    }
            
    return m;
    }
   
    //Méthode matTest2
    static Matrice matTest2(int n){
    Matrice m = matTest1(n);
    for(int i=0; i<n; i++){
        m.coeffs[i][i]=m.coeffs[i][i]*(-1);
    }
            
    return m;
    }
    
    //Méthode aleaUnOuDeux
    static Matrice aleaUnOuDeux(int nl, int nc){
        
        Matrice id= new Matrice(nl,nc);
        for(int i=0;i<nl;i++)
        {
            for(int j=0;j<nc;j++)
            {
                id.coeffs[i][j]=((int)(Math.random()*10))%2+1;

            }
        }
        return id;   
      }
    
    //Méthode matAleaZeroUnDeux
    static Matrice matAleaZeroUnDeux(int nl, int nc, double pz){
        Matrice m = new Matrice(nl, nc);
        for(int i=0; i<nl; i++) {
            for(int j=0; j<nc; j++) {
                
                m.coeffs[i][j]=Math.random();
                
                if (m.coeffs[i][j]<=pz) { 
                   m.coeffs[i][j]=0;
                }else{
                   m.coeffs[i][j]=((int)(Math.random()*10)%2)+1;
                }
            }
        }
        return m;
    }
    
    static Matrice creeVecteur(double[] t){
        Matrice m= new Matrice(1,t.length);
        for(int i=0;i<t.length;i++){
            m.coeffs[0][i] = t[i];        
        
    }
        return m;
}
    public Matrice concatLig(Matrice m ){

        if (this.getnbrCol() != m.getnbrCol()) {
            throw new Error("les matrices doivent avoir même nombre de colonnes");
        }
        Matrice res = new Matrice(this.getnbrLig() + m.getnbrLig(), this.getnbrCol());
        for (int i = 0; i < res.getnbrLig(); i++) {
            for (int j = 0; j < res.getnbrCol(); j++) {
                if (i < this.getnbrLig()) {
                    res.set(i, j, this.get(i, j));
                } else {
                    res.set(i, j, m.get(i - this.getnbrLig(), j));
                }
            }
        }
        return res;
    }

    public Matrice concatCol(Matrice m2) {
      
        if (this.getnbrLig() != m2.getnbrLig()) {
            throw new Error("les matrices doivent avoir même nombre de lignes");
        }
        Matrice res = new Matrice(this.getnbrLig(), this.getnbrCol() + m2.getnbrCol());
        for (int i = 0; i < res.getnbrLig(); i++) {
            for (int j = 0; j < res.getnbrCol(); j++) {
                if (j < this.getnbrCol()) {
                    res.set(i, j, this.get(i, j));
                } else {
                    res.set(i, j, m2.get(i, j - this.getnbrCol()));
                }
            }
        }
        return res;
    }
    //--------------- partie 2.2

    public Matrice subLignes(int ligMin, int ligMax) {
        if (ligMin < 0 || ligMax < ligMin || ligMax >= this.getnbrLig()) {
            throw new Error("indices lignes invalides");
        }
        Matrice res = new Matrice(ligMax - ligMin + 1, this.getnbrCol());
        for (int lig = 0; lig < res.getnbrLig(); lig++) {
            for (int col = 0; col < res.getnbrCol(); col++) {
                res.set(lig, col, this.get(ligMin + lig, col));
            }
        }
        return res;
    }

    public Matrice subCols(int colMin, int colMax) {
        if (colMin < 0 || colMax < colMin || colMax >= this.getnbrCol()) {
            throw new Error("indices colonnes invalides");
        }
        Matrice res = new Matrice(this.getnbrLig(), colMax - colMin + 1);
        for (int lig = 0; lig < res.getnbrLig(); lig++) {
            for (int col = 0; col < res.getnbrCol(); col++) {
                res.set(lig, col, this.get(lig, colMin + col));
            }
        }
        return res;
    }

    public Matrice copie() {
        return this.subLignes(0, this.getnbrLig() - 1);
    }

    //--------------- partie 2.3
    public Matrice transposee() {
        Matrice res = new Matrice(this.getnbrCol(), this.getnbrLig());
        for (int i = 0; i < res.getnbrLig(); i++) {
            for (int j = 0; j < res.getnbrCol(); j++) {
                res.set(i, j, this.get(j, i));
            }
        }
        return res;
    }

    //--------------- partie 2.4
    public Matrice metAuCarre() {
        return this.concatCol(Matrice.identite(this.getnbrLig())).concatLig(
                Matrice.identite(this.getnbrCol()).concatCol(this.transposee()));
    }

    public static int intAlea(int bmin, int bmax) {
        return (int) (Math.random() * (bmax - bmin + 1)) + bmin;
    }

    public static void test2() {
        System.out.println("----------- test 2 --------------");
        int nl = intAlea(1, 4), nc = intAlea(1, 4);
        double pz = 0.33;
        Matrice m = Matrice.matAleaZeroUnDeux(nl, nc, pz);
        Matrice mc = m.metAuCarre();
        Matrice mbis = mc.subLignes(0, m.getnbrLig() - 1).subCols(0, m.getnbrCol() - 1);
        System.out.println("m : matrice de base : ");
        System.out.println(m);
        System.out.println("mc : mise au carré : ");
        System.out.println(mc);
        System.out.println("mbis : (sub de mc) : ");
        System.out.println(mbis);
    }

    //--------------- partie 3.1
   
    public Matrice add(Matrice m2) {
        if (this.getnbrLig() != m2.getnbrLig() || this.getnbrCol() != m2.getnbrCol()) {
            throw new Error("tailles incompatibles pour add");
        }
        Matrice m = new Matrice(this.getnbrLig(), this.getnbrCol());
        for (int i = 0; i < m.getnbrLig(); i++) {
            for (int j = 0; j < m.getnbrCol(); j++) {
                m.set(i, j, this.get(i, j) + m2.get(i, j));
            }
        }
        return m;
    }

    //--------------- partie 3.2
    
    public Matrice opp() {
        Matrice res = new Matrice(this.getnbrLig(), this.getnbrCol());
        for (int i = 0; i < res.getnbrLig(); i++) {
            for (int j = 0; j < res.getnbrCol(); j++) {
                res.set(i, j, -this.get(i, j));
            }
        }
        return res;
    }

    //--------------- partie 3.3
   
    public Matrice moins(Matrice m2) {
        return this.add(m2.opp());
    }

    //--------------- partie 3.4
    public Matrice mult(Matrice m2) {
        if (this.getnbrCol() != m2.getnbrLig()) {
            throw new Error("tailles incompatibles pour mult");
        }
        Matrice m = new Matrice(this.getnbrLig(), m2.getnbrCol());
        for (int i = 0; i < m.getnbrLig(); i++) {
            for (int j = 0; j < m.getnbrCol(); j++) {
                double nb = 0;
                for (int k = 0; k < this.getnbrCol(); k++) {
                    nb = nb + this.get(i, k) * m2.get(k, j);
                }
                m.set(i, j, nb);
            }
        }
        return m;
    }
    //--------------- partie 3.5

    public static void test3() {
        System.out.println("----------- test 3 --------------");
        Matrice m = Matrice.matTest1(3);
        System.out.println("mat M : ");
        System.out.println(m);
        System.out.println("M + M^2 :");
        System.out.println(m.add(m.mult(m)));
    }

    //PIVOT DE GAUSS
    //partie 4


    public int permutLigne(int i1, int i2){

        int p=0;

        for( int j = 0; j<this.nbrCol; j++){

            if (this.coeffs[i1][j] ==this.coeffs[i2][j]){
                p=1;
            }else{
                p = -1;
                break;
            }
        }
       
        return p;
    }

    public void permutLign(int l1,int l2){

    double[] temp=new double[this.nbrCol];

        for(int i=0;i<this.nbrCol;i++){
            temp[i]=this.get(l1,i);
           
        }
        //on met L1 dans une tableau temporaire

        for(int i =0; i< this.getnbrCol();i++){
            this.set(l1,i,this.coeffs[l2][i]);
        }
        //on remplit L1 avec L2
        for(int i =0; i< this.getnbrCol();i++){
            this.set(l2,i,temp[i]);
        }
            

        }

    

    public Matrice transvection(int i1, int i2){

        if (this.coeffs[i1][i1] == 0){
            

        }else{
        double p = (this.coeffs[i2][i1])/(this.coeffs[i1][i1]);

        for (int j=0 ; j< this.nbrCol; j++){
            if(j==i1){
                this.coeffs[i2][j] = 0;
            }else{
                this.coeffs[i2][j]=this.coeffs[i2][j]-p*this.coeffs[i1][j];
            }
        }
    }
        return this;
    }

    public int lignePlusGrandPivot(int e){
    
    int i=e;
    double imax=this.coeffs[i][e];
    System.out.println("le max est "+imax);
    int compteur0=0;
    int compteurl=0;
    int test=0;
        
    while ((test !=-1)&& (i<this.getnbrLig())){
        System.out.println(" ");
        System.out.println(" ligne+gdpivot etape "+ i);
        
        if (Math.abs(this.coeffs[i][e])>=Math.abs(imax)){

            imax=Math.abs(this.coeffs[i][e]);
            
            System.out.println("le max de la colonne est : "+imax);
            compteurl=i;

        }else{
            if(this.coeffs[i][e]==0){
                compteur0++;
            }
        }
        i++;
    }
    if(compteur0==this.nbrLig-e){
        test=-1;
    }else{
        test=compteurl;
    }

    if (test<=epsilon_pivot){
        test=-1;
    }
    
    return test;

        
    }

    public int lignePlusGrandPivot2(int e) {
        if (e >= this.getnbrLig() || e >= this.getnbrCol()) {
            throw new Error("mauvais indice de pivot : M_e,e doit exister");
        }
        double curMax = Math.abs(this.get(e, e));
        int imax = e;
        for (int i = e + 1; i < this.getnbrLig(); i++) {
            if (Math.abs(this.get(i, e)) > curMax) {
                curMax = Math.abs(this.get(i, e));
                imax = i;
            }
        }
        if (curMax <= epsilon_pivot) {
            return -1;
        } else {
            return imax;
        }

    }

    public void descenteGauss(){
        
        int lpivot;
        if(this.getnbrCol()==this.getnbrLig()){
             //Pour chaque colonne, on cherche le plus grand pivot
        for(int i=0;i<this.getnbrCol();i++){
           
            lpivot=lignePlusGrandPivot(i);
            System.out.println("la ligne pivot max est "+String.valueOf(lpivot));
        //On permute ensuite les ligne afin de mettre le pivot au bon endroit
            
            if (this.permutLigne(lpivot,i)==1){
               
            }else{
                this.permutLign(lpivot, i);
            }
            System.out.println("matrice apres permutations numero "+ String.valueOf(i));
            System.out.println(this.toString());
            //une fois que les lignes sont permutees, on fait une transvection
                for(int j=i+1;j<this.getnbrLig();j++){
                    this.transvection(i, j);
                }
        //transvection entre la ligne afin de "supprimer" les éléments 
        System.out.println("matrice apres transvection numero "+ String.valueOf(i));
        
            System.out.println(this.toString());
          
        }
        }else{

        
        //Pour chaque colonne, on cherche le plus grand pivot
        for(int i=0;i<this.getnbrCol()-1;i++){
           
            lpivot=lignePlusGrandPivot(i);
           
        //On permute ensuite les ligne afin de mettre le pivot au bon endroit
       
            if (this.permutLigne(lpivot,i)==1){
               
                
            }else{
                this.permutLign(lpivot, i);
                
            }
            System.out.println("matrice apres permutations numero "+ String.valueOf(i));

            System.out.println(this.toString());
            //une fois que les lignes sont permutees, on fait une transvection

                for(int j=i+1;j<this.getnbrLig();j++){
                    this.transvection(i, j);
                }
        //transvection entre la ligne afin de "supprimer" les éléments 
        System.out.println("matrice apres transvection numero "+ String.valueOf(i));
        
            System.out.println(this.toString());
            
        }
    }
     

      
    }

    public void transvection2(int lig1, int lig2) {
        if (lig1 >= this.getnbrCol()) {
            throw new Error("lig1 doit être inférieur au nombre de colonnes de la matrice");
        }
        if (this.get(lig1, lig1) == 0) {
            throw new Error("pivot nul : ligne " + lig1 + " Mat :\n" + this);
        }
        double p = this.get(lig2, lig1) / this.get(lig1, lig1);
        for (int col = 0; col < this.getnbrCol(); col++) {
            if (col == lig1) {
                this.set(lig2, col, 0);
            } else {
                this.set(lig2, col, this.get(lig2, col) - p * this.get(lig1, col));
            }
        }
    }
    

    public ResGauss descenteGauss2() {
        int e = 0;
        int imax;
        int signature = 1;
        while (e < this.getnbrLig() && e < this.getnbrCol()
                && (imax = this.lignePlusGrandPivot2(e)) != -1) {
            signature = signature * this.permuteLigneV2(e, imax);
            for (int lig = e + 1; lig < this.getnbrLig(); lig++) {
                this.transvection2(e, lig);
            }
            e++;
        }
        System.out.println("le rang est = "+e);
        return new ResGauss(e, signature);
    }

public int permuteLigneV2(int lig1, int lig2) {
        // on utilise ici le fait que les tableaux à deux dimensions en java
        // sont en fait des tableaux de tableaux.
        // voir ci-dessous une autre définition qui ne se sert pas de cette
        // propriété, et qui sera sans doute l'implémentation proposée par
        // les étudiants
        for (int col = 0; col < this.getnbrCol(); col++) {
            double temp = this.get(lig1, col);
            this.set(lig1, col, this.get(lig2, col));
            this.set(lig2, col, temp);
        }
        if (Math.abs(lig2 - lig2) % 2 == 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public double determinant() {
        if (this.getnbrLig() != this.getnbrCol()) {
            throw new Error("déterminant défini uniquement pour les matrices carrées");
        }
        Matrice ms = this.copie();
        ResGauss tri = ms.descenteGauss2();
        if (tri.getrang() != this.getnbrLig()) {
            return 0;
        } else {
            double det = tri.getsignature();
            for (int k = 0; k < this.getnbrLig(); k++) {
                det = det * ms.get(k, k);
            }
            return det;
        }
    }
    public static void testdebutpartie4(Matrice m){
        m.transvection(0,2);
        System.out.println();
        afficheTableau(m,m.getnbrLig(),m.getnbrLig());
  
  
        int test;
        int l1 = 1;
        int l2 = 2;
       test= m.permutLigne(l1,l2);
       System.out.println(test);
      
    }
    public static void testpivot(Matrice m){
        int t;
        System.out.println("entrer un entier pour tester la ligne pivot");
        t=Lire.i();
        t=m.lignePlusGrandPivot(1);
        System.out.println(t);
    }

    public void pivotsUnitaires(int rang) {
        for (int lig = 0; lig < rang; lig++) {
            double div = this.get(lig, lig);
            if (div == 0) {
                throw new Error("pivot nul : ligne " + lig + " Mat :\n" + this);
            }
            for (int col = 0; col < this.getnbrCol(); col++) {
                this.set(lig, col, this.get(lig, col) / div);
            }
        }
    }

    public void remonteegauss(int rang ) {


        for (int e = rang - 1; e >= 0; e--) {
            for (int lig = e - 1; lig >= 0; lig--) {
                this.transvection2(e, lig);
            }
        }
    }

   
    public static class ResSysLin {


        private boolean solUnique;
        private Matrice sol;
       
        private ResSysLin() {
            this.solUnique = false;
        }
       
        private ResSysLin(Matrice sol) {
            this.solUnique = true;
            this.sol = sol;
        }
       
        public static ResSysLin pasDeSolUnique() {
            return new ResSysLin();
        }
       
        public static ResSysLin sol(Matrice sol) {
            return new ResSysLin(sol);
        }
       
        public String toString() {
            if (this.solUnique) {
                return this.sol.toString();
            } else {
                return "{pas de sol unique}";
            }
        }
       
       }
       
       public Matrice getColonne(int col) {
        Matrice res = new Matrice(this.getnbrLig(), 1);
        for (int lig = 0; lig < this.getnbrLig(); lig++) {
            res.set(lig, 0, this.get(lig, col));
        }
        return res;
       }
       
       public ResSysLin resoudSysLin(Matrice secondMembre) {
        if (this.getnbrLig() != this.getnbrCol()) {
            throw new Error("La matrice n'est pas carrée");
        }
        if (secondMembre.getnbrCol() != 1) {
            throw new Error("Le second membre n'est pas un vecteur");
        }
        Matrice toGauss = this.concatCol(secondMembre);
        ResGauss res = toGauss.descenteGauss2();
        if (res.getrang() == this.getnbrLig()) {
            toGauss.pivotsUnitaires(res.getrang());
            toGauss.remonteegauss(res.getrang());
            return ResSysLin.sol(toGauss.getColonne(res.getrang()));
        } else {
            return ResSysLin.pasDeSolUnique();
        }
       }
       
       
       
    //Méthode principale main
    
    public static void main(String[] args){
            int nl=3, nc=4;
          Matrice m = new Matrice(nl,nc);
          //Affiche du tableau des coefficients
          for (int i=0; i< nl; i++){
              for (int j=0; j< nc; j++){
                  
                  m.set(i,j,Lire.d());
                
              }
        
      }
      
      m.transvection(0, 2);
      System.out.println(m.toString());
   
     System.out.println();
      m.descenteGauss();
   
   
}
}