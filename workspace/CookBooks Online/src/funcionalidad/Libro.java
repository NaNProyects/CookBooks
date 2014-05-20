package funcionalidad;

public class Libro {
		private int isbn;
		private String titulo;
		private String autor; // pasar a objeto autor ?
		private String genero;
		private String editorial;
		private String idioma;
		private String reseña;
		private String vistaso;
		private double precio;
		public Libro(Long i, String titulo, String autor, String genero,
				String editorial,String idioma , String reseña, String vistaso, Double precio) {
			super();
			this.isbn = i.intValue();
			this.titulo = titulo;
			this.autor = autor;
			this.genero = genero;
			this.editorial = editorial;
			this.idioma = idioma;
			this.reseña = reseña;
			this.vistaso = vistaso;
			this.precio = precio.doubleValue();
		}
		public int getIsbn() {
			return isbn;
		}
		public void setIsbn(int isbn) {
			this.isbn = isbn;
		}
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		public String getAutor() {
			return autor;
		}
		public void setAutor(String autor) {
			this.autor = autor;
		}
		public String getGenero() {
			return genero;
		}
		public void setGenero(String genero) {
			this.genero = genero;
		}
		public String getEditorial() {
			return editorial;
		}
		public void setEditorial(String editorial) {
			this.editorial = editorial;
		}
		public String getReseña() {
			return reseña;
		}
		public void setReseña(String reseña) {
			this.reseña = reseña;
		}
		public String getVistaso() {
			return vistaso;
		}
		public void setVistaso(String vistaso) {
			this.vistaso = vistaso;
		}
		public double getPrecio() {
			return precio;
		}
		public void setPrecio(double precio) {
			this.precio = precio;
		}
		public String getIdioma() {
			return idioma;
		}
		public void setIdioma(String idioma) {
			this.idioma = idioma;
		}
		
}
