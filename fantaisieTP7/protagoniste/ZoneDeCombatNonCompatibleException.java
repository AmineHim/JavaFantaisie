/**
 * 
 */
package protagoniste;

/**
 * @author amine
 *
 */
public class ZoneDeCombatNonCompatibleException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception{
		try {
			throw new Exception("1");
		}catch(Exception ex) {
			throw new Exception("2");
		}
	}

}
