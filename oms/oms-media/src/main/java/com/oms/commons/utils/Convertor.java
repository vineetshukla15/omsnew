package com.oms.commons.utils;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.oms.model.RateCard;
import com.oms.viewobjects.PaginationResponseVO;
import com.oms.viewobjects.RateCardVO;

public class Convertor {

	public static <T, R> Collection<R> convert(Collection<T> list, Function<T, R> convertor) {
		return list.stream().map(convertor).collect(Collectors.toList());
	}

	public static <T, R> PaginationResponseVO<R> toPaginationVO(PaginationResponseVO<T> t, Function<T, R> mapper) {
		PaginationResponseVO<R> r = new PaginationResponseVO<>();
		r.setDraw(t.getDraw());
		r.setRecordsFiltered(t.getRecordsFiltered());
		r.setRecordsTotal(t.getRecordsTotal());
		r.setData(convert(t.getData(), mapper));
		return r;
	}

	public static RateCardVO toRateCardVO(RateCard rateCard) {
		if (rateCard == null)
			return null;
		return new RateCardVO(rateCard);
	}


}
