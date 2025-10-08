package net.ausiasmarch.whosthatpokemon.model;


//dao para mostrar el score del usuario
public class ScoreBean {

	private int id;
	private int userId;
	private int score;
	private int tries;
	private String username;

	//costructor vacio
	public ScoreBean() {
	}

	//constructor con parámetros
	public ScoreBean(int id, int userId, int score, int tries, String username) {
		this.id = id;
		this.userId = userId;
		this.score = score;
		this.tries = tries;
		this.username = username;
	}

	//getters y setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getTries() {
		return tries;
	}

	public void setTries(int tries) {
		this.tries = tries;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
