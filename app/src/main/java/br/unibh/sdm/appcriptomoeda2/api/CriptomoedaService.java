package br.unibh.sdm.appcriptomoeda2.api;

import java.util.List;

import br.unibh.sdm.appcriptomoeda2.entidades.Criptomoeda;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CriptomoedaService {

    @Headers({
            "Accept: application/json",
            "User-Agent: AppCriptomoeda"
    })
    @GET("criptomoeda")
    Call<List<Criptomoeda>> getCriptomoedas();


}
