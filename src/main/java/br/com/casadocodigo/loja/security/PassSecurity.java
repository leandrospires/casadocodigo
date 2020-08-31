package br.com.casadocodigo.loja.security;

public class PassSecurity {

	public static void main(String[] args) {
		System.out.println(new PassSecurity().generate("123"));
	}
	
	public String generate (String senhaTexto) {
		
		return null;
		
		/*try {
			
			byte[] digest = MessageDigest.getInstance("sha-256").digest(senhaTexto.getBytes());
			
			return Base64Encoder.encode(digest);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		*/
	}
}
