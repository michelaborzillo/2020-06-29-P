package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	PremierLeagueDAO dao;
	Graph<Match, DefaultWeightedEdge> grafo;
	List<Match> gare;
	
	public Model() {
		dao= new PremierLeagueDAO();
	}
	
	
	public void creaGrafo(Mese mese, int tempo) {
		this.grafo= new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		gare= dao.listAllMatches(mese);
		Graphs.addAllVertices(this.grafo, gare);
		for (Match m1: this.gare) {
			for (Match m2: this.gare) {
				if (!m1.equals(m2)) {
					int peso= dao.getPeso(tempo, m1, m2);
					if( peso > 0) {
						Graphs.addEdge(this.grafo, m1, m2, peso);
					}
				}
			}
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
		
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	
	}
	
}
