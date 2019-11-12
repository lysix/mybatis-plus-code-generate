/**
 * Copyright 2019-2029 geekidea(https://github.com/geekidea)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mybatisplus.base.api.common.constant;

/**
 * 常量
 * @author geekidea
 * @date 2018-11-08
 */
public interface CommonConstant {

    /**
     * 默认页码为1
     */
    Integer DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认页大小为10
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 查询时间字段的名称，默认为 “create_date ”
     */
    String DEFAULT_DATE_FIELD_NAME= "create_date";

}
