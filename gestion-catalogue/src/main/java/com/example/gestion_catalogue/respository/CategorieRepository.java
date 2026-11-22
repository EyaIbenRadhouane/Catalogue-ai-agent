package org.example.mcpserver.respository;


import org.example.mcpserver.entities.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {
    Categorie findByNom(String Nom);
}
