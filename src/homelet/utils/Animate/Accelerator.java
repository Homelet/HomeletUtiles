package homelet.utils.Animate;

public interface Accelerator{
	
	/**
	 * the Linear Accelerator
	 */
	Accelerator LINEAR      = new Liner();
	/**
	 * the typical slow first accelerator
	 */
	Accelerator PARABOLA    = new Parabola();
	/**
	 * the typical fast first accelerator
	 */
	Accelerator SQUARE_ROOT = new SquareRoot();
	
	double f(double x);
	
	double reverse(double y);
	
	class Liner implements Accelerator{
		
		@Override
		public double f(double x){
			return x;
		}
		
		@Override
		public double reverse(double y){
			return y;
		}
	}
	
	class Parabola implements Accelerator{
		
		@Override
		public double f(double x){
			return Math.pow(x, 2);
		}
		
		@Override
		public double reverse(double y){
			return Math.pow(y, 0.5);
		}
	}
	
	class SquareRoot implements Accelerator{
		
		@Override
		public double f(double x){
			return Math.pow(x, 0.5);
		}
		
		@Override
		public double reverse(double y){
			return Math.pow(y, 2);
		}
	}
}
