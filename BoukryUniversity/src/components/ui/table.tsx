import React from 'react';

interface TableProps {
  columns: { key: string; label: string; render?: (item: any) => React.ReactNode }[];
  data: any[];
  onRowClick?: (item: any) => void;
  isLoading?: boolean;
}

export function Table({ columns, data, onRowClick, isLoading }: TableProps) {
  if (isLoading) {
    return (
      <div className="w-full text-center py-12">
        <div className="inline-block h-8 w-8 animate-spin rounded-full border-[3px] border-slate-200 border-t-teal-600"></div>
        <p className="mt-3 text-sm font-medium text-slate-500">កំពុងផ្ទុកទិន្នន័យ...</p>
      </div>
    );
  }

  if (!data || data.length === 0) {
    return (
      <div className="w-full text-center py-12 bg-slate-50/50 rounded-lg border border-dashed border-slate-200">
        <p className="text-sm text-slate-500">រកមិនឃើញទិន្នន័យ។</p>
      </div>
    );
  }

  return (
    <div className="overflow-x-auto bg-white rounded-lg border border-slate-200 shadow-sm">
      <table className="w-full text-sm text-left text-slate-700">
        <thead className="text-[13px] text-slate-600 bg-slate-50 border-b border-slate-200">
          <tr>
            {columns.map((col) => (
              <th key={col.key} scope="col" className="px-5 py-3.5 font-semibold">
                {col.label}
              </th>
            ))}
          </tr>
        </thead>
        <tbody className="divide-y divide-slate-100">
          {data.map((item, idx) => (
            <tr
              key={item.id || idx}
              onClick={() => onRowClick && onRowClick(item)}
              className={"transition-colors hover:bg-slate-50 " + (onRowClick ? 'cursor-pointer' : '')}
            >
              {columns.map((col) => (
                <td key={col.key} className="px-6 py-4">
                  {col.render ? col.render(item) : item[col.key]}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
