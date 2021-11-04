package com.klarna.smoothie;

import org.junit.Assert;


/**
 * @author sanray on 9/19/2021
 */
public class SmoothieTest {
    public static void main(String[] args) {
        Assert.assertEquals("strawberry,banana,mango,peach,honey",
                Smoothie.ingredients("Classic"));
        Assert.assertEquals("banana,mango,peach,honey",
                Smoothie.ingredients("Classic,-strawberry,-peanut"));
        Assert.assertEquals("strawberry,mango,peach,honey",
                Smoothie.ingredients("Classic,-banana,-peanut"));

        Assert.assertEquals("blackberry,blueberry,black currant,grape juice,frozen yogurt",
                Smoothie.ingredients("Freezie,-tea"));


        try {
            Smoothie.ingredients("Classic,mango");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients("Vitamin Smootihe");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients(null);
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients("");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients(",");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients("-,-");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients("Classic,-");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients("Freezie ,-");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

        try {
            Smoothie.ingredients("Freezie,tea");
            Assert.fail("failed");
        } catch (IllegalArgumentException ilex) {
            Assert.assertEquals("pass", true, true);
        }

    }
}
