package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.maluku.sma_rt.DataBinderMapperImpl());
  }
}
