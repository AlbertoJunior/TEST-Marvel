package com.example.desafio_android_alberto_junior;

import com.example.desafio_android_alberto_junior.model.Character;
import com.example.desafio_android_alberto_junior.vm.CharacterViewModel;
import com.example.desafio_android_alberto_junior.vm.ComicViewModel;

import org.junit.Test;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class CharacterViewModelTest {
    @Test
    public void getCurrent() {
        CharacterViewModel viewModel = new CharacterViewModel();
        assertNull(viewModel.getCurrent().get());
    }

    @Test
    public void setCurrent() {
        Character character = new Character();
        character.setName("Assert Hero");

        CharacterViewModel viewModel = new CharacterViewModel();
        assertNull(viewModel.getCurrent().get());

        viewModel.setCurrent(character);
        assertNotNull(viewModel.getCurrent().get());
        assertEquals("Assert Hero", viewModel.getCurrent().get().getName());
    }

    @Test
    public void verifyShowAndHideElements() {
        CharacterViewModel viewModel = new CharacterViewModel();
        viewModel.setShowLoading(true);
        assertEquals(viewModel.getShowLoading().get(), VISIBLE);
        viewModel.setShowLoading(false);
        assertEquals(viewModel.getShowLoading().get(), GONE);

        viewModel.setShowError(false);
        assertEquals(viewModel.getShowError().get(), GONE);
        viewModel.setShowError(true);
        assertEquals(viewModel.getShowError().get(), VISIBLE);

        viewModel.setShowSearchButton(false);
        assertEquals(viewModel.getShowSearchButton().get(), GONE);
        viewModel.setShowSearchButton(true);
        assertEquals(viewModel.getShowSearchButton().get(), VISIBLE);

        viewModel.setShowEmpty(true);
        assertEquals(viewModel.getShowEmpty().get(), VISIBLE);
        viewModel.setShowEmpty(false);
        assertEquals(viewModel.getShowEmpty().get(), GONE);
    }

    @Test
    public void getOffSet() {
        CharacterViewModel viewModel = new CharacterViewModel();
        assertEquals(0, viewModel.getOffset());
        assertFalse(viewModel.getOffset() != 0);
    }

    @Test
    public void setOffSet() {
        CharacterViewModel viewModel = new CharacterViewModel();
        assertEquals(0, viewModel.getOffset());
        assertFalse(viewModel.getOffset() != 0);

        viewModel.setOffset(9);
        assertEquals(9, viewModel.getOffset());
    }

    @Test
    public void updateOffSet() {
        CharacterViewModel viewModel = new CharacterViewModel();
        assertEquals(0, viewModel.getOffset());
        assertFalse(viewModel.getOffset() != 0);

        viewModel.updateOffset();
        viewModel.updateOffset();
        assertEquals(2, viewModel.getOffset());
    }

    @Test
    public void resetOffSet() {
        CharacterViewModel viewModel = new CharacterViewModel();
        assertEquals(0, viewModel.getOffset());
        viewModel.updateOffset();
        viewModel.updateOffset();
        assertEquals(2, viewModel.getOffset());
        viewModel.setOffset(9);
        viewModel.updateOffset();
        assertEquals(10, viewModel.getOffset());
        viewModel.updateOffset();
        assertEquals(0, viewModel.getOffset());
    }
}
