package com.ms.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

		/*
		This class provide the methods for MSWebElement
		
		*/

public class MSWebElementList extends ArrayList<MSWebElement> {

	private WebDriver driver;
	private List<MSWebElement> eleList = new ArrayList<MSWebElement>();

	public MSWebElementList(WebDriver driver, By loc) {
		this.driver = driver;
		for (WebElement ele : this.driver.findElements(loc)) {
			eleList.add(new MSWebElement(driver, ele));
		}
	}

	public MSWebElementList(MSWebElement parentElement, By childElements) {
		for (WebElement indEle : parentElement.findElements(childElements)) {
			eleList.add((MSWebElement) indEle);
		}
	}

	@Override
	public String toString() {
		if (eleList != null) {
			return eleList.toString();
		} else {
			return null;
		}
	}

	@Override
	public int size() {
		return eleList.size();
	}

	@Override
	public boolean isEmpty() {
		return eleList.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return eleList.contains(o);
	}

	@Override
	public Iterator<MSWebElement> iterator() {
		return eleList.iterator();
	}

	@Override
	public Object[] toArray() {
		return eleList.toArray();
	}

	@Override
	public boolean add(MSWebElement e) {
		return eleList.add(e);
	}

	@Override
	public boolean remove(Object o) {
		return eleList.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return eleList.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends MSWebElement> c) {
		return eleList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends MSWebElement> c) {
		return eleList.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return eleList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return eleList.retainAll(c);
	}

	@Override
	public void clear() {
		eleList.clear();
	}

	@Override
	public MSWebElement get(int index) {
		return (MSWebElement) eleList.get(index);
	}

	@Override
	public MSWebElement set(int index, MSWebElement element) {
		return (MSWebElement) eleList.set(index, element);
	}

	@Override
	public void add(int index, MSWebElement element) {
		eleList.add(index, element);
	}

	@Override
	public MSWebElement remove(int index) {
		return (MSWebElement) eleList.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return eleList.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return eleList.lastIndexOf(o);
	}

	@Override
	public ListIterator<MSWebElement> listIterator() {
		return eleList.listIterator();
	}

	@Override
	public ListIterator<MSWebElement> listIterator(int index) {
		return eleList.listIterator(index);
	}

	@Override
	public List<MSWebElement> subList(int fromIndex, int tolndex) {
		return eleList.subList(fromIndex, tolndex);
	}

}
