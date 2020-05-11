package com.example.desafio_android_alberto_junior;

import com.example.desafio_android_alberto_junior.model.Comic;
import com.example.desafio_android_alberto_junior.model.ComicPrice;
import com.example.desafio_android_alberto_junior.vm.ComicViewModel;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ComicViewModelTest {
    @Test
    public void getCurrent() {
        ComicViewModel viewModel = new ComicViewModel();
        assertNull(viewModel.getCurrent().get());
    }

    @Test
    public void setCurrent() {
        Comic comic = new Comic();
        comic.setTitle("Assert Hero #1 - Testing");

        ComicViewModel viewModel = new ComicViewModel();
        assertNull(viewModel.getCurrent().get());

        viewModel.setCurrent(comic);
        assertNotNull(viewModel.getCurrent().get());
        assertEquals("Assert Hero #1 - Testing", viewModel.getCurrent().get().getTitle());
    }

    @Test
    public void verifySetCurrentShow() {
        ComicViewModel viewModel = new ComicViewModel();
        Comic comic = new Comic();
        viewModel.setCurrent(comic);
        assertNotNull(viewModel.getCurrent().get());
        assertEquals(viewModel.getShowCurrent().get(), VISIBLE);
    }

    @Test
    public void testSearchMostValuableNull() {
        ComicViewModel viewModel = new ComicViewModel();
        Comic comic = viewModel.searchMostValuable();
        assertNull(comic);
    }

    @Test
    public void testSearchMostValuable() {
        ComicViewModel viewModel = new ComicViewModel();

        List<Comic> comics = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < 19; i++) {
            Comic comic = new Comic();
            comic.setTitle(String.format("Assert Hero #%d - Testing", i));
            // gerando uma quantia aleatoria de ComicPrice
            setComicPrices(comic, r, r.nextInt(5) + 1);
            comics.add(comic);
        }

        Comic comic = new Comic();
        comic.setTitle("[GOLD VERSION] Assert Hero #20 - Testing");
        setComicPrice(comic, 200);
        comics.add(comic);

        viewModel.setComicList(comics);
        viewModel.setListFinish(true);

        Comic comicMostValuable = viewModel.searchMostValuable();
        assertNotNull(comicMostValuable);
        assertEquals(comicMostValuable.getTitle(), comic.getTitle());
        assertNotEquals(150, comicMostValuable.getAuxPrice(), 0.0);
        assertEquals(200, comicMostValuable.getAuxPrice(), 0.0);
    }

    @Test
    public void verifyListFinish() {
        ComicViewModel viewModel = new ComicViewModel();
        // notificando que a lista finalizou
        viewModel.setListFinish(true);
        // verifica se ocultou o loading
        assertEquals(viewModel.getShowLoading().get(), GONE);
        // vai tentar buscar o mais valioso e não vai encontrar, tem que exibir o erro
        assertEquals(viewModel.getShowError().get(), VISIBLE);
    }

    private void setComicPrices(@NotNull Comic comic, Random r, int qtdPrices) {
        List<ComicPrice> prices = new ArrayList<>();
        for (int i = 0; i < qtdPrices; i++) {
            ComicPrice comicPrice = new ComicPrice();
            // gerando preços aleatórios
            comicPrice.setPrice(r.nextInt(100));
            comicPrice.setType(String.format("br[%d]", i));
            prices.add(comicPrice);
        }
        comic.setPrices(prices);
    }

    private void setComicPrice(@NotNull Comic comic, int val) {
        ComicPrice comicPrice = new ComicPrice();
        comicPrice.setPrice(val);
        comicPrice.setType("br");
        comic.setPrices(Collections.singletonList(comicPrice));
    }
}
