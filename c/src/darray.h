#ifndef _darray_
#define _darray_
#ifdef TYPE
#ifndef TYPED
#define TYPED(t) t
#endif
#include <stdlib.h>
#include <assert.h>
#include <string.h>

#define DEFAULT_CAPACITY 16

typedef struct TYPED(Arr) {
	size_t capacity;
	size_t len;
	TYPE* elements;
} TYPED(Arr);

TYPED(Arr) TYPED(Arr_new)() {
	TYPED(Arr) arr;
	TYPE* elements = (TYPE*) malloc(sizeof(TYPE)*DEFAULT_CAPACITY);
	arr.len = 0;
	arr.capacity = DEFAULT_CAPACITY;
	arr.elements = elements;
	return arr;
}

void TYPED(Arr_add)(TYPED(Arr)* arr, TYPE val) {
	if (arr->len == arr->capacity) {
		arr->elements = (TYPE*) realloc(arr->elements, arr->capacity * sizeof(TYPE) * 2);
		arr->capacity *= 2;
	}

	arr->elements[arr->len] = val;
	arr->len++;
}

void TYPED(Arr_del)(TYPED(Arr)* arr, size_t idx) {
	assert(arr->len > idx);
	memmove(&arr->elements[idx], &arr->elements[idx + 1], arr->len - idx);
	arr->len--;
}

TYPE TYPED(Arr_get)(TYPED(Arr)* arr, size_t idx) {
	assert(arr->len > idx);
	return arr->elements[idx];
}

void TYPED(Arr_free)(TYPED(Arr)* arr) {
	free(arr->elements);
}

#endif
#endif
