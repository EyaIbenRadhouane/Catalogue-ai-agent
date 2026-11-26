package org.example.mcpserver.tools;


import lombok.AllArgsConstructor;
import org.example.mcpserver.entities.Categorie;
import org.example.mcpserver.entities.Produit;
import org.example.mcpserver.respository.CategorieRepository;
import org.example.mcpserver.respository.ProduitRepository;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class ProductTools {
    private CategorieRepository categorieRepository;
    private ProduitRepository produitRepository;

    @Tool(name = "getProduct", description = "recuperer le produit par son id")
    public Produit getProduit(@ToolParam(description = "id du produit") Long id) {

        return produitRepository.findById(id).orElse(null);
    }

    @Tool(name = "allProducts", description = "recupere la liste de tous les produits directement depuis la base de données")
    public List<Produit> getAllProduits() {

        return produitRepository.findAll();
    }

    @Tool(name = "searchProducts", description = "Rechercher des produits dont le nom contient un mot-cle donne")
    public List<Produit> searchProducts(@ToolParam(description = "Mot-cle de recherche", required = true) String keyword) {
        return this.produitRepository.findByNomContains(keyword);
    }

   // @Tool(name = " getProduitsParMC ", description = " Recupere les produits d’une categorie donnee par son ID")
    //public List<Produit> getProduitsParMC(@ToolParam(description = " Nom de la categorie ", required = true) String mc) {

     //   return produitRepository.findByNomContains(mc);
    //}

    @Tool(
            name = "getCatalogueStatistics",
            description = "Fournir les statistiques du catalogue telles que le nombre total de produits, le prix moyen arrondi à 2 decimales, le produit le plus cher et le moins cher"
    )
    public Map<String, Object> getCatalogueStatistics() {

        Map<String, Object> map = new HashMap<>();

        List<Produit> liste = produitRepository.findAll();

        map.put("nombre de produits", liste.size());

        map.put(
                "prix moyen",
                liste.stream()
                        .mapToDouble(p -> p.getPrix())
                        .average()
                        .orElse(0)
        );

        map.put(
                "produit le plus cher",
                liste.stream()
                        .max((p1, p2) ->
                                Double.compare(p1.getPrix(), p2.getPrix())
                        )
                        .orElse(null)
        );

        map.put(
                "produit le moins cher",
                liste.stream()
                        .min((p1, p2) ->
                                Double.compare(p1.getPrix(), p2.getPrix())
                        )
                        .orElse(null)
        );

        return map;
    }
    @Tool(name = "addProduct", description = "Ajoute un nouveau produit a la base de donnees")
    public String addProduct(
            @ToolParam(description = "Nom du produit") String nom,
            @ToolParam(description = "Prix du produit") double prix,
            @ToolParam(description = "Quantite du produit") int quantite,
            @ToolParam(description = "Nom de la categorie") String nomCategorie) {

        if (prix < 0) {
            return "Prix invalide";
        }
        if (quantite < 0) {
            return "Quantite invalide";
        }

        Categorie c = categorieRepository.findByNom(nomCategorie);
        if (c == null) {
            return "Categorie non valide";
        }

        Produit p = new Produit();
        p.setNom(nom);
        p.setQuantite(quantite);
        p.setPrix(prix);
        p.setCategorie(c);
        produitRepository.save(p);

        return "Produit ajoute avec succes : " + nom;
    }

}
