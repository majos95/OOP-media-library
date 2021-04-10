package m19.core;

public enum Category {
	REFERENCE{
		public String toString() {
			return "Referência";
		}
	}, 
	FICTION{
		public String toString() {
			return "Ficção";
		}
	}, 
	SCITECH{
		public String toString() {
			return "Técnica e Científica";
		}
	};	
}
