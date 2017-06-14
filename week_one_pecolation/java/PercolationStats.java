public class PercolationStats {  
   private double staT[];    
   private double staMean;    
   private double staStddev;    
   private int N;
   
   public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
   {
       staT=new double[T];
       this.N=N;
       int times=0;
       if(N<=0) throw new IllegalArgumentException();
       if(T<=0) throw new IllegalArgumentException();
       while(times<T){
           Percolation pe=new Percolation(N);
           int count=0;
           while(true){
               count++;
               while(true){
                   int x = StdRandom.uniform(N) + 1;  
                   int y = StdRandom.uniform(N) + 1;
                   if(pe.isOpen(x, y)){
                       continue;
                   }else{
                       pe.open(x, y);
                       break;
                   }
                   
               }
               if(pe.percolates()){
                   staT[times]=(double)count/((double)N*(double)N);
                   break;
               }
           }
           times++;
       }
       this.staMean = StdStats.mean(staT);  
       this.staStddev = StdStats.stddev(staT);
   }
   public double mean()                      // sample mean of percolation threshold  
   {
       return this.staMean;
   }  
   public double stddev()                    // sample standard deviation of percolation threshold  
   {
       return this.staStddev;
   }
   public double confidenceLo()              // low  endpoint of 95% confidence interval
   {
       return this.staMean-1.96*this.staStddev/Math.sqrt(N);
   }
   public double confidenceHi()              // high endpoint of 95% confidence interval  
   {
       return this.staMean+1.96*this.staStddev/Math.sqrt(N);
   }
   public static void main(String[] args)    // test client (described below)  
   {
         int N = StdIn.readInt();  
         int T = StdIn.readInt();  
         PercolationStats percolationStats = new PercolationStats(N, T);  
         StdOut.println("mean = " + percolationStats.mean());  
         StdOut.println("stddev = " + percolationStats.stddev());  
         StdOut.println("95% confidence interval " + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi());  
   }
}