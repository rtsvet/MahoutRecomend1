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
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
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
        try {
            DataModel model =
                    new FileDataModel(new File("intro.csv"));
            UserSimilarity similarity =
                    new PearsonCorrelationSimilarity(model);
            UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
            Recommender recommender = new GenericUserBasedRecommender(
                    model, neighborhood, similarity);
            List<RecommendedItem> recommendations =
                    recommender.recommend(1, 2);
            for (RecommendedItem recommendation : recommendations) {
                System.out.println(recommendation);
            }
        } catch (IOException ex) {
            Logger.getLogger(MahoutRecomend1.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TasteException tex) {
            Logger.getLogger(MahoutRecomend1.class.getName()).log(Level.SEVERE, null, tex);
        }
    }
    
}
