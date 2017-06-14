public class Percolation{
    private WeightedQuickUnionUF uf;
    private int N;
    private boolean[] openState;
    
    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {
        if (N<=0) throw new IllegalArgumentException("N is<=0");
        this.N=N;
        int i;
        uf=new WeightedQuickUnionUF((N+1)*N+1);
        openState=new boolean[(N+1)*N+1];
        openState[0*N+1]=true;
        openState[(N+1)*N+1]=true;
        for(i=1;i<=N;i++)
        {
            uf.union(0*N+1,N*1+i);
            uf.union((N+1)*N+1,N*N+i);
            uf.union(0*N+1,N*1+i);
        }
    }
    public void open(int i, int j)          // open site (row i, column j) if it is not open already  
    {
        if (i<1||i>N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if(j<1||j>N)  throw new IndexOutOfBoundsException("column index j out of bounds");
        if(openState[i*N+j]) return;
        openState[i*N+j]=true;
        if (openState[(i-1)*N+j]){  
            uf.union(i*N+j, (i-1)*N+j);
        } 
        if (openState[(i+1)*N+j]){  
            uf.union(i*N+j, (i+1)*N+j);
        }  
        if (j!=1 && openState[i*N+j-1]){
            uf.union(i*N+j, i*N+j-1);
        }
        if (j!=N && openState[i*N+j+1]){
            uf.union(i*N+j, i*N+j+1);
        }
    }
    public boolean isOpen(int i, int j)     // is site (row i, column j) open?  
    {
        if (i<1||i>N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j<1||j>N) throw new IndexOutOfBoundsException("column index j out of bounds");
        return openState[i*N+j];
    }
    public boolean isFull(int i, int j)     // is site (row i, column j) full?  
    {
        if (i<1||i>N) throw new IndexOutOfBoundsException("row index i out of bounds");
        if (j<1||j>N) throw new IndexOutOfBoundsException("column index j out of bounds");
        return uf.connected(i*N+j, 0*N+1)&& openState[i*N+j];
    }
    public boolean percolates()             // does the system percolate? 
    {
        return uf.connected(0*N+1, N*N+1);
    }
}
