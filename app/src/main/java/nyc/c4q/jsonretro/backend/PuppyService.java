package nyc.c4q.jsonretro.backend;

import nyc.c4q.jsonretro.model.RandoPuppy;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by joannesong on 12/17/17.
 */

public interface PuppyService {
    @GET("api/breeds/image/random")
    Call<RandoPuppy> getPuppy();
}
