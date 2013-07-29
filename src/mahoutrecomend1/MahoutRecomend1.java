package mahoutrecomend1;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.knn.KnnItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.knn.NonNegativeQuadraticOptimizer;
import org.apache.mahout.cf.taste.impl.recommender.knn.Optimizer;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

/**
 *
 * @author rtsvet
 */
public class MahoutRecomend1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Starting the Recomender ...");
        try {
            DataModel model =
                    new FileDataModel(new File("intro.csv"));
            /*
             UserSimilarity similarity =
             new PearsonCorrelationSimilarity(model);
             UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
             Recommender recommender = new GenericUserBasedRecommender(
             model, neighborhood, similarity);
             */
            // Item similarity
            ItemSimilarity similarity = new LogLikelihoodSimilarity(model);
            Optimizer optimizer = new NonNegativeQuadraticOptimizer();
            Recommender recommender = new KnnItemBasedRecommender(model, similarity, optimizer, 1);


            long userId = 1;
            List<RecommendedItem> recommendations =
                    recommender.recommend(userId, 2);
            for (RecommendedItem recommendation : recommendations) {
                System.out.println("UserID[" + userId + "] would like: " + recommendation);
            }
        } catch (IOException ex) {
            Logger.getLogger(MahoutRecomend1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TasteException tex) {
            Logger.getLogger(MahoutRecomend1.class.getName()).log(Level.SEVERE, null, tex);
        }
    }
}
