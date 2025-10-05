package net.ausiasmarch.whosthatpokemon.model;

import java.time.LocalDateTime;

//dao para mostrar el score del usuario
public class ScoreDao {

	private int id;
	private int userId;
	private int score;
	private int tries;
	private String username;

	//costructor vacio
	public ScoreDao() {
	}

	//constructor con parámetros
	public ScoreDao(int id, int userId, int score, int tries, LocalDateTime timestamp, String username) {
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
