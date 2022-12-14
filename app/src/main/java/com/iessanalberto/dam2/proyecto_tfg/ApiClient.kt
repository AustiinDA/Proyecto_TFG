package com.iessanalberto.dam2.proyecto_tfg

import com.iessanalberto.dam2.proyecto_tfg.interfaces.TheMovieDBService
import com.iessanalberto.dam2.proyecto_tfg.modelos.GetMovieById
import com.iessanalberto.dam2.proyecto_tfg.modelos.GetMovieCreditsById
import com.iessanalberto.dam2.proyecto_tfg.recursos.Constantes
import retrofit2.Response

class ApiClient(
    private val theMovieDBService: TheMovieDBService
) {
    suspend fun getMovieById(movieId: Int): SimpleResponse<GetMovieById> {
        return llamadaApiSegura {
            theMovieDBService.getMovieById(
                movieId,
                Constantes.API_KEY,
                Constantes.LANGUAGE
            )
        }
    }

    suspend fun getMovieCreditsById(movieId: Int): SimpleResponse<GetMovieCreditsById> {
        return llamadaApiSegura {
            theMovieDBService.getMovieCreditsById(
                movieId,
                Constantes.API_KEY,
                Constantes.LANGUAGE
            )
        }
    }

    // Funcion parametrizada que recibe un indicador de excepciones y devuelve una respuesta parametrizada
    private inline fun <T> llamadaApiSegura(llamadaApi: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(llamadaApi.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}